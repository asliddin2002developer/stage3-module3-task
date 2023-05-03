package com.mjc.school.controller.menumanager;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.CommandDispatcher;
import com.mjc.school.repository.exception.NotFoundException;
import com.mjc.school.repository.utils.NewsParams;
import com.mjc.school.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mjc.school.controller.menumanager.Menu.*;
import static com.mjc.school.controller.menumanager.Utils.*;

@Component
public class MenuManager {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;
    private final BaseController<TagDtoRequest, TagDtoResponse, Long> tagController;
    private final CommandDispatcher commandDispatcher;
    private final InputHandler inputHandler;
    private final Menu menu;


    @Autowired
    public MenuManager(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController,
                       BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController,
                       BaseController<TagDtoRequest, TagDtoResponse, Long> tagController,
                       CommandDispatcher commandDispatcher,
                       InputHandler inputHandler,
                       Menu menu) {
        this.authorController = authorController;
        this.newsController = newsController;
        this.tagController = tagController;
        this.commandDispatcher = commandDispatcher;
        this.inputHandler = inputHandler;
        this.menu = menu;
    }


    public void run() {
        long authorId;
        long newsId;
        long tagId;
        String name;
        String title;
        String content;
        Set<Long> tagIds;
        do {
            menu.display();
            String choice = inputHandler.ask("");
            List<Object> commandParams = new ArrayList<>();
            switch (choice) {
                case READ_AUTHOR_BY_ID -> {
                    authorId = inputHandler.isValidId( inputHandler.ask( ENTER_AUTHOR_ID.getContent() ) );
                    commandParams.add( authorId );
                }
                case READ_NEWS_BY_ID -> {
                    newsId = inputHandler.isValidId( inputHandler.ask( ENTER_NEWS_ID.getContent() ) );
                    commandParams.add( newsId );
                }
                case READ_TAG_BY_ID -> {
                    tagId = inputHandler.isValidId( inputHandler.ask( ENTER_TAG_ID.getContent() ) );
                    commandParams.add( tagId );
                }
                case CREATE_AUTHOR -> {
                    name = inputHandler.ask( ENTER_AUTHOR_NAME.getContent() );
                    commandParams.add( new AuthorDtoRequest( name ) );
                }
                case CREATE_NEWS -> {
                    title = inputHandler.ask( ENTER_NEWS_TITLE.getContent() );
                    content = inputHandler.ask( ENTER_NEWS_CONTENT.getContent() );
                    authorId = inputHandler.isValidId( inputHandler.ask( ENTER_AUTHOR_ID.getContent() ) );
                    tagIds = getTagIds();
                    AuthorDtoResponse author = authorController.readById( authorId );
                    commandParams.add( new NewsDtoRequest( title, content, author, tagIds ) );
                }
                case CREATE_TAG -> {
                    name = inputHandler.ask( ENTER_TAG_NAME.getContent() );
                    commandParams.add( new TagDtoRequest(name) );
                }
                case UPDATE_AUTHOR -> {
                    authorId = inputHandler.isValidId( inputHandler.ask( ENTER_AUTHOR_ID.getContent() ) );
                    name = inputHandler.ask( ENTER_AUTHOR_NAME.getContent() );
                    commandParams.add( new AuthorDtoRequest( authorId, name ) );
                }
                case UPDATE_NEWS -> {
                    newsId = inputHandler.isValidId( inputHandler.ask( ENTER_NEWS_ID.getContent() ) );
                    title = inputHandler.ask( ENTER_NEWS_TITLE.getContent() );
                    content = inputHandler.ask( ENTER_NEWS_CONTENT.getContent() );
                    authorId = inputHandler.isValidId( inputHandler.ask( ENTER_AUTHOR_ID.getContent() ) );
                    tagIds = getTagIds();
                    AuthorDtoResponse author = authorController.readById( authorId );
                    commandParams.add( new NewsDtoRequest( newsId, title, content, author, tagIds ) );
                }
                case UPDATE_TAG -> {
                    tagId = inputHandler.isValidId( inputHandler.ask( ENTER_TAG_ID.getContent() ) );
                    name = inputHandler.ask( ENTER_TAG_NAME.getContent() );
                    commandParams.add( new TagDtoRequest( tagId, name ) );
                }
                case DELETE_AUTHOR_BY_ID -> {
                    authorId = inputHandler.isValidId( inputHandler.ask( ENTER_AUTHOR_ID.getContent() ) );
                    commandParams.add( authorId );
                }
                case DELETE_NEWS_BY_ID -> {
                    newsId = inputHandler.isValidId( inputHandler.ask( ENTER_NEWS_ID.getContent() ) );
                    commandParams.add( newsId );
                }
                case DELETE_TAG_BY_ID -> {
                    tagId = inputHandler.isValidId( inputHandler.ask( ENTER_TAG_ID.getContent() ) );
                    commandParams.add( tagId );
                }
                case FIND_TAGS_BY_NEWS_ID -> {
                    newsId = inputHandler.isValidId( inputHandler.ask( ENTER_NEWS_ID.getContent() ) );
                    commandParams.add( newsId );
                }
                case FIND_AUTHORS_BY_NEWS_ID -> {
                    newsId = inputHandler.isValidId( inputHandler.ask( ENTER_NEWS_ID.getContent() ) );
                    commandParams.add( newsId );
                }
                case FIND_NEWS_BY_PARAMS -> {
                    NewsParams newsParams = this.fillNewsParams();
                    commandParams.add( newsParams );
                }
                case EXIT -> System.exit(0);
            }

            BaseController<?, ?, ?> controller = this.getRelevantController( choice ); // controller which is passed to commandDispatcher as an argument

            try {
                commandDispatcher.dispatch( controller, choice, commandParams );
            } catch (Exception e) {
                System.out.println("Failed to execute command: " + choice);
                e.printStackTrace();
            }
        } while (true);
    }

    private Set<Long> getTagIds() {
        List<String> tagIdsString = List.of( inputHandler.ask( ENTER_TAG_IDS.getContent() ).split(","));
        Set<Long> tagIdsLong = new HashSet<>();
        Long tmp;
        for ( String id : tagIdsString ){
            tmp = inputHandler.isValidId( id );
            if (tmp != null){
                tagIdsLong.add( tmp );
            }
        }
        return tagIdsLong;
    }

    private NewsParams fillNewsParams() {
        String authorName = inputHandler.ask( ENTER_AUTHOR_NAME.getContent() );
        String title = inputHandler.ask( ENTER_NEWS_TITLE.getContent() );
        String content = inputHandler.ask( ENTER_NEWS_CONTENT.getContent() );
        String tagIds = inputHandler.ask( ENTER_TAG_IDS.getContent() );
        List<String> tagIdsString = List.of( tagIds.split(",") );
        List<Long> tagIdsLong = new ArrayList<>();
        for (var tag : tagIdsString){
            try {
                tagIdsLong.add( inputHandler.isValidId(tag) );
            }
            catch (RuntimeException e){
                e.printStackTrace();
            }
        }
        String tagNamesString = inputHandler.ask( ENTER_TAG_NAMES.getContent() );
        List<String> tagNames = List.of( tagNamesString.split(",") );

        return new NewsParams( authorName, title, content, tagIdsLong, tagNames );
    }

    public BaseController<?, ?, ?> getRelevantController( String choice ){
        switch (choice) {
            case READ_ALL_AUTHORS, READ_AUTHOR_BY_ID, CREATE_AUTHOR, UPDATE_AUTHOR, DELETE_AUTHOR_BY_ID, FIND_AUTHORS_BY_NEWS_ID -> {
                return authorController;
            }
            case READ_ALL_NEWS, READ_NEWS_BY_ID, CREATE_NEWS, UPDATE_NEWS, DELETE_NEWS_BY_ID, FIND_NEWS_BY_PARAMS -> {
                return newsController;
            }
            case READ_ALL_TAGS, READ_TAG_BY_ID, CREATE_TAG, UPDATE_TAG, DELETE_TAG_BY_ID, FIND_TAGS_BY_NEWS_ID -> {
                return tagController;
            }
            case EXIT -> System.exit(0);
        }
        throw new NotFoundException("Enter proper command");
    }
}

