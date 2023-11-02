package de.epischel.gitlabbot.actions;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;

public class AddCommentAction {
    
    public void addComment(final GitLabApi api, final MergeRequest mergeRequest, final String comment) throws GitLabApiException {
        api.getNotesApi().createMergeRequestNote(mergeRequest.getProjectId(), mergeRequest.getIid(), comment);
    }
    
}
