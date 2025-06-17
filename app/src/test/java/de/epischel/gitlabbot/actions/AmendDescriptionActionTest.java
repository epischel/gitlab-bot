package de.epischel.gitlabbot.actions;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.MergeRequestApi;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestParams;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmendDescriptionActionTest {
    @Test
    void appendsTextToDescription() throws Exception {
        GitLabApi api = mock(GitLabApi.class);
        MergeRequestApi mrApi = mock(MergeRequestApi.class);
        when(api.getMergeRequestApi()).thenReturn(mrApi);

        MergeRequest mr = new MergeRequest();
        mr.setProjectId(5L);
        mr.setIid(10L);
        mr.setDescription("desc");

        new AmendDescriptionAction().amendDescription(api, mr, "extra");

        ArgumentCaptor<MergeRequestParams> captor = ArgumentCaptor.forClass(MergeRequestParams.class);
        verify(mrApi).updateMergeRequest(eq(5L), eq(10L), captor.capture());

        Field desc = MergeRequestParams.class.getDeclaredField("description");
        desc.setAccessible(true);
        assertEquals("desc\nextra", desc.get(captor.getValue()));
    }
}
