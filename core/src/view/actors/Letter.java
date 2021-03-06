package view.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import view.AssetManager;

/**
 * Each Letter can have a different spelling if it is a tone.
 * @authors Derick Lenvik, Jared Johnson
 */
public class Letter extends TextButton {
    private String spelling;

    public Letter(String name, int size) {
        super(name, AssetManager.textButtonStyle64);
        if (size < 100) {
            setStyle(AssetManager.textButtonStyle32);
        }
        setName(name);
        spelling = name;
        setSize(size, size);
    }

    public Letter(Letter letter, int size) {
        this(letter.getName(), size);
        spelling = letter.spelling;
    }

    // If tone, trim actual spelling to last lowercase character.
    public void setIsTone() {
        spelling = getName().substring(getName().length() - 1).toLowerCase();
    }

    public String getSpelling() {
        return spelling;
    }
}
