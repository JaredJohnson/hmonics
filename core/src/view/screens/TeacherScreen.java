package view.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import view.AssetManager;
import view.GdxGame;
import viewmodel.ScreenManager;

/**
 *
 */
public class TeacherScreen implements Screen {
    private GdxGame game;
    public static Stage stage;
    private Table mainTable;
    private Sound clickSound;

    public TeacherScreen(GdxGame gdxGame) {
        this.game = gdxGame;
        stage = new Stage(gdxGame.viewport, gdxGame.batch);
        Gdx.input.setInputProcessor(stage);

        setStage();
    }

    private void setStage() {
        mainTable = new Table();
        mainTable.top().left().setBounds(0, 0, GdxGame.WIDTH, GdxGame.height);
        mainTable.setBackground(new TextureRegionDrawable(AssetManager.getTextureRegion("background")));
        stage.addActor(mainTable);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sounds/SFX/BigClick.mp3"));

        selectTeachers();
    }

    private void selectTeachers() {
        String titleText = "Add or remove teachers. \nSelect a teacher to see their students.";
        ChangeListener doOnBackButton = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                ScreenManager.setScreen(new StartScreen(TeacherScreen.this.game));
            }
        };
        ChangeListener doAfterSelectItem = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                TeacherScreen.this.selectStudents();
            }
        };

        String addItemInfoText = "<Teacher name>";
        ChangeListener doAfterAddRemove = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TeacherScreen.this.selectTeachers();
            }
        };

        mainTable.clearChildren();
        mainTable.addActor(ScreenManager.screenFactory(ScreenManager.ScreenType.TEACHERS, titleText, doOnBackButton, doAfterSelectItem, addItemInfoText, doAfterAddRemove));
    }

    private void selectStudents() {
        String titleText = "Add or remove students. \nSelect a student to see their history.";
        ChangeListener doOnBackButton = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                TeacherScreen.this.selectTeachers();
            }
        };
        ChangeListener doAfterSelectItem = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                displayHistories();
            }
        };

        String addItemInfoText = "<Student name>";
        ChangeListener doAfterAddRemove = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                TeacherScreen.this.selectStudents();
            }
        };

        mainTable.clearChildren();
        mainTable.addActor(ScreenManager.screenFactory(ScreenManager.ScreenType.STUDENTS, titleText, doOnBackButton, doAfterSelectItem, addItemInfoText, doAfterAddRemove));
    }

    private void displayHistories() {
        String titleText = "Student's game history:";
        ChangeListener doOnBackButton = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play();
                TeacherScreen.this.selectStudents();
            }
        };

        mainTable.clearChildren();
        mainTable.addActor(ScreenManager.screenFactory(ScreenManager.ScreenType.HISTORIES, titleText, doOnBackButton, null, null, null));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        clickSound.dispose();
        stage.dispose();
    }
}
