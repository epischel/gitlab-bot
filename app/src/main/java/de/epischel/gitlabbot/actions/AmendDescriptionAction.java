package de.epischel.gitlabbot.actions;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestParams;

/**
 * This class provides a method to amend the description of a MergeRequest using the gitlab4j-api.
 * 
 */
public class AmendDescriptionAction {
    
    // method to Amend string to Description of a MergeRequest (gitlab4j-api)   
    public void amendDescription(final GitLabApi api, final MergeRequest mergeRequest, final String description) throws GitLabApiException {
        var newDescription = mergeRequest.getDescription() + "\n" + description;
        var params = new MergeRequestParams().withDescription(newDescription);
        api.getMergeRequestApi().updateMergeRequest(
            mergeRequest.getProjectId(), 
            mergeRequest.getIid(), 
            params
        );
    }    

}
