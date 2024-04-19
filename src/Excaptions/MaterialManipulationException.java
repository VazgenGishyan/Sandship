package Excaptions;

import static Sandship.Constants.ANSI_RED;
import static Sandship.Constants.ANSI_RESET;

public class MaterialManipulationException extends Exception {
    private final MaterialManipulationExceptionType exceptionType;

    public MaterialManipulationException(MaterialManipulationExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public void what() {
        switch (exceptionType) {
            case NEGATIVE_QUANTITY -> {
                System.out.println(ANSI_RED + "NEGATIVE_QUANTITY Exception: Quantity can not be negative" + ANSI_RESET);
            }
            case INSUFFICIENT_QUANTITY -> {
                System.out.println(ANSI_RED + "INSUFFICIENT_QUANTITY Exception: Insufficient quantity of material for operations" + ANSI_RESET);
            }
        }
    }
}
