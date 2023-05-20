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

    public User currentAuthorOrElseThrow(User existingUser) {
        var currentU = authService
                .getCurrentUser()
                .orElseThrow(NotFoundException::new);

        if (!currentU.getId().equals(existingUser.getId())
            && !(currentU.getAccessLevel() == User.Access.MOD)
            && !(currentU.getAccessLevel() == User.Access.ADMIN)) {
            throw new NotAllowedException();
        }

        return currentU;
    }

    public User currentAuthorOrElseThrow(Image existingImage) {
        var currentU = authService
                .getCurrentUser()
                .orElseThrow(NotFoundException::new);

        if (!currentU.getId().equals(existingImage.getUploader().getId())
            && !(currentU.getAccessLevel() == User.Access.MOD)
            && !(currentU.getAccessLevel() == User.Access.ADMIN)) {
            throw new NotAllowedException();
        }

        return currentU;
    }

    public User currentAuthorOrElseThrow(Campaign existingCampaign) {
        var currentU = authService
                .getCurrentUser()
                .orElseThrow(NotFoundException::new);

        if (!currentU.getId().equals(existingCampaign.getCampaigner().getId())
            && !(currentU.getAccessLevel() == User.Access.MOD)
            && !(currentU.getAccessLevel() == User.Access.ADMIN)) {
            throw new NotAllowedException();
        }

        return currentU;
    }

    private User currentAuthorOrElseThrow(Reply existingReply) {
        var currentU = authService
                .getCurrentUser()
                .orElseThrow(NotFoundException::new);

        if (!currentU.getId().equals(existingReply.getUser().getId())
            && !(currentU.getAccessLevel() == User.Access.MOD)
            && !(currentU.getAccessLevel() == User.Access.ADMIN)) {
            throw new NotAllowedException();
        }

        return currentU;
    }
}
