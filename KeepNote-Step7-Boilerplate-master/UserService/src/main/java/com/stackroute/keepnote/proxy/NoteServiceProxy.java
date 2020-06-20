package com.stackroute.keepnote.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("note-service")
public interface NoteServiceProxy {

    @GetMapping("/api/v1/note/{userId}/{noteId}")
    public ResponseEntity<?> getNotes(@PathVariable String userId, @PathVariable int noteId);
}
