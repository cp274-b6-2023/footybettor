import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        gui test = new gui();
        Generate gen = new Generate();

        String input = test.getButtonResult();
        System.out.println("System Input: " + input);


        String output = "";

        output = gen.generateText(input, "TestingFile.txt");

        test.changeReturnLabel("Generated Text: " + output); // this method takes a string given by the generator class and updates the gui
        test.repaint();

    }

}
