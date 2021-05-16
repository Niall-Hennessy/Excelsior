package ux;

import javafx.stage.Screen;

//  Set the content messages for the help modal accessed from the menuBar Help button
public class HelpMenuConstructor {

    HelpMenu helpMenu = new HelpMenu();

     public HelpMenuConstructor(){

        helpMenu.setWidth(Screen.getPrimary().getVisualBounds().getWidth()/1.3);
        helpMenu.setHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);

        helpMenu.createTab("Character");
        helpMenu.createTab("Speech Bubbles");
        helpMenu.createTab("Skin/Hair");
        helpMenu.createTab("Caption");
        helpMenu.createTab("Backgrounds");
        helpMenu.createTab("Lock/Unlock");
        helpMenu.createTab("Undo/Delete");
        helpMenu.createTab("Save/Load");
        helpMenu.createTab("Add Backgrounds/Characters");

        helpMenu.setTabContent("Character", "\n Let's add a character to your comic!\n " +
                "\n First select a panel so that it is highlighted." +
                "\n Click on the character icon to choose a left or right character." +
                "\n Click a character pose from the gallery." +
                "\n Use the Flip Button to change which way they are facing." +
                "\n Use the M/F button to change their gender." +
                "\n Hint: The characters are draggable!");

        helpMenu.setTabContent("Speech Bubbles", "\n Let's get your characters talking!\n " +
                "\n Note: You have to have a character in your panel before you can make them talk.\n " +
                "\n Click on the speech bubble icon." +
                "\n Choose what bubble you want." +
                "\n Write in the text-box what you want them to say." +
                "\n Choose if you want the text in italic, or bold, or both." +
                "\n Hit Submit and voila!" +
                "\n Hit Cancel if you change your mind." +
                "\n Hit Delete if you want to get rid of the bubble.\n" +
                "\n Hint: The bubbles are draggable!");

        helpMenu.setTabContent("Skin/Hair", "\n Let's add some colour!\n " +
                "\n Select the character who's Skin/Hair you wish to change." +
                "\n Select the Skin/Hair colour picker to select a new colour.");

        helpMenu.setTabContent("Caption", "\n Let's caption your panel!\n " +
                "\n Hit the caption button." +
                "\n The default caption area is the Top." +
                "\n To choose an area to caption select either 'Top Text' or 'Bottom Text', both can be used." +
                "\n Write what you want the caption to be." +
                "\n Choose a font for you caption." +
                "\n Hit 'Apply' and see it appear." +
                "\n Hit 'Cancel' if you change your mind." +
                "\n Hit 'Delete' after selecting either the 'Top Text' or 'Bottom Text' to remove the caption.");

        helpMenu.setTabContent("Backgrounds", "\n Let's add a background!\n" +
                "\n Hit the background button and click the image you want to use.");

        helpMenu.setTabContent("Lock/Unlock", "\n Let's protect your work!\n" +
                "\n When you have finished working on your panel, to prevent accidental changes hit the open lock button." +
                "\n This prevents changes to the panel until you unlock it." +
                "\n To unlock the panel hit the now closed lock button." +
                "\n Note: The panel will be unlocked if you hit undo as well.");

        helpMenu.setTabContent("Undo/Delete", "\n Undo: \n" +
                "\n Hit the back arrow button to undo or hit 'z' on your keyboard.\n" +
                "\n Delete:\n" +
                "\n Hit the red trash can button to delete the selected panel. ");

        helpMenu.setTabContent("Save/Load", "\n Save HTML:\n" +
                "\n Click on File beside Help."+
                "\n Click on Save HTML." +
                "\n Follow the prompt and set the Title of your Comic Strip." +
                "\n Choose the number of rows and columns for the display arrangement of the Comic Strip." +
                "\n Choose the dimensions of your Comic Panels from the option box." +
                "\n If you change your mind just hit Cancel." +
                "\n Choose where to save it and what to call the file.\n" +
                "\n Save XML:\n" +
                "\n Click on File beside Help." +
                "\n Click on Save XML." +
                "\n Choose where to save it and what to call the file.\n"+
                "\n Load XML:\n" +
                "\n Click on File beside Help." +
                "\n Click on Load XML." +
                "\n Find the file in your folders and hit Open.\n"
        );

        helpMenu.setTabContent("Add Backgrounds/Characters", "\n Click on the File beside Help.\n" +
                "\n Choose to add either a new Background or a new Character." +
                "\n Find them in your folder and hit Open." +
                "\n Select the Background/Character button." +
                "\n Find the new image in the gallery and select it to use it."
        );
    }

    public void show(){
        helpMenu.showHelpMenu();
    }
}
