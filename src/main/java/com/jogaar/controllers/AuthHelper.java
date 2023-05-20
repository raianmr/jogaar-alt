package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.NotAllowedException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.Image;
import com.jogaar.entities.Reply;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthHelper {
    private final AuthService authService;

    public User currentUserOrElseThrow() {
        return authService
                .getCurrentUser()
                .orElseThrow(NotFoundException::new);
    }

    public User currentAuthorOrElseThrow(User existingUser) {
        var currentU = currentUserOrElseThrow();

        if (!currentU.getId().equals(existingUser.getId()) && !isSuperUser(currentU)) {
            throw new NotAllowedException();
        }

        return currentU;
    }

    public User currentAuthorOrElseThrow(Image existingImage) {
        return currentAuthorOrElseThrow(existingImage.getUploader());
    }

    public User currentAuthorOrElseThrow(Campaign existingCampaign) {
        return currentAuthorOrElseThrow(existingCampaign.getCampaigner());
    }

    private User currentAuthorOrElseThrow(Reply existingReply) {
        return currentAuthorOrElseThrow(existingReply.getUser());
    }

    public boolean isSuperUser(User user) {
        return user.getAccessLevel() == User.Access.ADMIN || user.getAccessLevel() == User.Access.MOD;
    }
}
