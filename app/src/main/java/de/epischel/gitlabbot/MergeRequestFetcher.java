package de.epischel.gitlabbot;

import java.util.ArrayList;
import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Constants.MergeRequestState;
import org.gitlab4j.api.models.Diff;

/**
 * This class fetches all open merge requests for a given GitLab project using the GitLab API.
 * It returns a list of MergeRequestInfo objects containing information about each merge request,
 * including notes and diffs.
 */
public class MergeRequestFetcher {
    
    public List<MergeRequestInfo> fetchOpenMergeRequests(final GitLabApi api, final String projectId) throws GitLabApiException {
        var mergeRequests = api.getMergeRequestApi().getMergeRequests(projectId, MergeRequestState.OPENED);
        var mergeRequestInfos = new ArrayList<MergeRequestInfo>();
        for (var mergeRequest : mergeRequests) {
            var mergeRequestNotes = api.getNotesApi().getMergeRequestNotes(projectId, mergeRequest.getIid());
            var mrDiffs = api.getMergeRequestApi().getMergeRequestDiffs(projectId, mergeRequest.getIid());
            List<Diff> diffs = new ArrayList<>();
            for (var mrDiff : mrDiffs) {
                if (mrDiff.getDiffs()!=null)
                  diffs.addAll(mrDiff.getDiffs());
            }
            mergeRequestInfos.add(new MergeRequestInfo(mergeRequest, mergeRequestNotes, diffs));
        }
        return mergeRequestInfos;
    }
}
