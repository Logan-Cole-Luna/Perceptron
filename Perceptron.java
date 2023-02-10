import java.util.Scanner;

public class Perceptron {

    private static double[][] inputArray = {{0, 0},
            {0, 1},
            {1, 0},
            {1, 1}};
    private static int[] y = {0, 0, 0, 1};
    private static double[] weights;
    private static double learningRate = 0.1;
    private static int activationFunction;
    // the max iterations of the process
    private static int maxIterations;
    private static int iterations;

    private static double correctPredictions = 0.0;

    public static void main(String[] args) {
        // User selects max # of iterations
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the max number of training iterations: ");
        maxIterations = sc.nextInt();

        // User selects function to use
        System.out.println("Select the activation function (1 = ReLU, 2 = Tanh, 3 = LeakyReLU, 4 = Sigmoid): ");
        System.out.println("Note: The sigmoid function never reaches 100% accuracy, and is present to display this issue ");
        activationFunction = sc.nextInt();

        // Creating weights & assigning low random number to weights
        weights = new double[inputArray[0].length];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 2 - 1.0;
            System.out.println(weights[i]);
        }
        int i = 0;
        // While loop that goes until accuracy reaches 100% OR user selected iteration is reached
        while (true){
            correctPredictions = 0;
            double predictedOutput = 0;
            double error = 0;
            for (int j = 0; j < inputArray.length; j++) {
                // Calculating accuracy of predictions
                predictedOutput = predictOutput(inputArray[j]);
                //the threshold is 0.5 (as the predicted output is rounded to either 0 or 1).
                if (Math.round(predictedOutput) == y[j]) {
                    correctPredictions++;
                }
                // Calculating weights
                error = y[j] - predictedOutput;
                // Updating weights
                for (int k = 0; k < weights.length; k++) {
                    weights[k] = weights[k] + learningRate * error * inputArray[j][k];
                }
            }
            // Printing out calculated results
            System.out.println("|Iteration " + (i + 1) + "| y = " + predictedOutput + "| error = " + error +"| Current accuracy = "+ correctPredictions / inputArray.length * 100 + "%|");
            System.out.println("Current Weights " + weights[0] + ", " + weights[1]);
            // Incrementing iteration for loop
            i++;
            // Ending loop if accuracing == 100%
            if ((correctPredictions / inputArray.length) == 1) {
                break;
            }
            // Ending loop if iterations == Max Iterations
            else if ( i == maxIterations){
                System.out.println("\nMax Iteration of " + maxIterations + " reached\n");
                break;
            }
        }
        // Printing final results
        System.out.println("\nFinal Iteration: " + (i));
        System.out.println("Final Weights: ");
        for (double weight : weights) {
            System.out.print(weight + " \n");
        }
        System.out.println();

        // Calculating final accuracy
        correctPredictions = 0;
        for (int l = 0; l < inputArray.length; l++) {
            double predictedOutput = predictOutput(inputArray[l]);
            //the threshold is 0.5 (as the predicted output is rounded to either 0 or 1).
            if (Math.round(predictedOutput) == y[l]) {
                correctPredictions++;
            }
            System.out.print("|PredictedOutput = " + Math.round(predictedOutput) + " |\t\t | y =  " + y[l]+"|\n");
        }
        System.out.println("Accuracy: " + correctPredictions / inputArray.length * 100 + "%");
    }

    // Function to calculate weighted sum and put this sum through the activation functions
    private static double predictOutput(double[] input) {
        double weightedSum = 0;
        for (int i = 0; i < input.length; i++) {
            weightedSum += input[i] * weights[i];
        }

        switch (activationFunction) {
            case 1:
                return ReLU(weightedSum);
            case 2:
                return tanh(weightedSum);
            case 3:
                return leakyReLU(weightedSum);
            case 4:
                return sigmoid(weightedSum);
            default:
                return 0;
        }
    }
    private static double ReLU(double x) {
        return Math.max(0, x);
    }
    // Tanh (Hyperbolic Tangent): This activation function is commonly used in recurrent neural networks. It maps input values between -1 and 1.
    private static double tanh(double x) {
        return Math.tanh(x);
    }
    // The leaky ReLU adds a small negative slope for negative input values.
    private static double leakyReLU(double x) {
        return Math.max(0.01 * x, x);
    }
    private static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
