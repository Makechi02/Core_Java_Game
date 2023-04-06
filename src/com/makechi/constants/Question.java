package com.makechi.constants;

import java.util.ArrayList;

public record Question(String questionText, ArrayList<String> choices, String correctChoice) {}