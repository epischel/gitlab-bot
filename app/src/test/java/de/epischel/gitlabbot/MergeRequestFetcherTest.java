package de.epischel.gitlabbot;

import de.epischel.gitlabbot.actions.*;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.NotesApi;
import org.gitlab4j.api.Constants.MergeRequestState;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestDiff;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Note;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MergeRequestFetcherTest {
    @Test
    void collectsMergeRequestInfo() throws Exception {
        GitLabApi api = mock(GitLabApi.class);
        MergeRequestApi mrApi = mock(MergeRequestApi.class);
        NotesApi notesApi = mock(NotesApi.class);
        when(api.getMergeRequestApi()).thenReturn(mrApi);
        when(api.getNotesApi()).thenReturn(notesApi);

        MergeRequest mr = new MergeRequest();
        mr.setIid(1L);
        mr.setProjectId(99L);
        List<MergeRequest> mrs = List.of(mr);
        when(mrApi.getMergeRequests("pid", MergeRequestState.OPENED)).thenReturn(mrs);

        Note note = new Note();
        when(notesApi.getMergeRequestNotes("pid", mr.getIid())).thenReturn(List.of(note));

        MergeRequestDiff diff = new MergeRequestDiff();
        diff.setDiffs(List.of(new Diff()));
        when(mrApi.getMergeRequestDiffs("pid", mr.getIid())).thenReturn(List.of(diff));

        List<MergeRequestInfo> result = new MergeRequestFetcher().fetchOpenMergeRequests(api, "pid");

        assertEquals(1, result.size());
        MergeRequestInfo info = result.get(0);
        assertSame(mr, info.mergeRequest());
        assertEquals(1, info.mergeRequestNotes().size());
        assertEquals(1, info.diffs().size());
    }
}
