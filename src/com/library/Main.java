package com.library;

import com.library.controller.LibraryController;

public class Main {

    public static void main(String[] args) {
        LibraryController controller = new LibraryController();
        controller.displayMenu();
    }
}