package JavaQuiz;
import java.util.*;

public class QuizApp {
    static class Option {
        String text;
        boolean correct;
        Option(String text, boolean correct) { this.text = text; this.correct = correct; }
    }

    static class Question {
        String prompt;
        List<Option> options;
        Question(String prompt, List<Option> options) {
            this.prompt = prompt;
            this.options = new ArrayList<>(options);
        }
        // shuffle options so order changes each run
        void shuffleOptions(Random rnd) {
            Collections.shuffle(options, rnd);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        List<Question> quiz = buildQuestions();
        // shuffle questions order (optional)
        Collections.shuffle(quiz, rnd);

        int score = 0;
        int qNum = 1;

        System.out.println("=== Welcome to the Java Console Quiz ===");
        System.out.println("Enter the option number for your answer. Type 'q' to quit.\n");

        for (Question q : quiz) {
            q.shuffleOptions(rnd);
            System.out.println("Q" + qNum + ": " + q.prompt);

            // display options
            for (int i = 0; i < q.options.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + q.options.get(i).text);
            }

            int choice = -1;
            while (true) {
                System.out.print("Your answer (1-" + q.options.size() + "): ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("q")) {
                    System.out.println("Quitting...");
                    printResult(score, qNum - 1);
                    sc.close();
                    return;
                }
                try {
                    choice = Integer.parseInt(line);
                    if (choice >= 1 && choice <= q.options.size()) break;
                } catch (NumberFormatException e) { /* fallthrough to retry */ }
                System.out.println("Invalid input. Please enter a number between 1 and " + q.options.size() + " or 'q' to quit.");
            }

            Option selected = q.options.get(choice - 1);
            if (selected.correct) {
                System.out.println("Correct!\n");
                score++;
            } else {
                // show which was correct
                String correctText = "";
                for (Option o : q.options) if (o.correct) correctText = o.text;
                System.out.println("Incorrect. Correct answer: " + correctText + "\n");
            }

            qNum++;
        }

        printResult(score, quiz.size());
        sc.close();
    }

    static void printResult(int score, int total) {
        System.out.println("=== Quiz Finished ===");
        System.out.println("Score: " + score + " out of " + total);
        double percent = total == 0 ? 0 : (score * 100.0 / total);
        System.out.printf("Percentage: %.2f%%\n", percent);
        if (percent == 100) System.out.println("Excellent!");
        else if (percent >= 70) System.out.println("Good job!");
        else System.out.println("Keep practicing!");
    }

    static List<Question> buildQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question(
            "Which keyword is used to inherit a class in Java?",
            Arrays.asList(
                new Option("implements", false),
                new Option("extends", true),
                new Option("inherits", false),
                new Option("super", false)
            )
        ));

        list.add(new Question(
            "Which collection allows duplicate elements and preserves insertion order?",
            Arrays.asList(
                new Option("HashSet", false),
                new Option("ArrayList", true),
                new Option("TreeSet", false),
                new Option("Hashtable", false)
            )
        ));

        list.add(new Question(
            "What is the size (in bytes) of an int in Java?",
            Arrays.asList(
                new Option("2 bytes", false),
                new Option("4 bytes", true),
                new Option("8 bytes", false),
                new Option("Depends on JVM", false)
            )
        ));

        list.add(new Question(
            "Which loop guarantees the body executes at least once?",
            Arrays.asList(
                new Option("for loop", false),
                new Option("while loop", false),
                new Option("do-while loop", true),
                new Option("enhanced for loop", false)
            )
        ));

        list.add(new Question(
            "Which interface provides methods to iterate over a Collection?",
            Arrays.asList(
                new Option("Iterable", true),
                new Option("Collection", false),
                new Option("Iteratorable", false),
                new Option("ListIterator", false)
            )
        ));

        return list;
    }
}
