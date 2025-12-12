package de.docfaust.blogapi.dto;

public record CreatePostRequest(String username, String title, String content) {}
