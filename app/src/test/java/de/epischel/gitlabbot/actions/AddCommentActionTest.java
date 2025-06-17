package de.epischel.gitlabbot.actions;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.NotesApi;
import org.gitlab4j.api.models.MergeRequest;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class AddCommentActionTest {
    @Test
    void addsCommentToMergeRequest() throws Exception {
        GitLabApi api = mock(GitLabApi.class);
        NotesApi notesApi = mock(NotesApi.class);
        when(api.getNotesApi()).thenReturn(notesApi);

        MergeRequest mr = new MergeRequest();
        mr.setProjectId(1L);
        mr.setIid(2L);

        new AddCommentAction().addComment(api, mr, "hello");

        verify(notesApi).createMergeRequestNote(1L, 2L, "hello");
    }
}
