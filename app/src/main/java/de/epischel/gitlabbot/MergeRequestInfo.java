package de.epischel.gitlabbot;

import java.util.List;

import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.Note;

/**
 * This class provides a public record to store information about a MergeRequest.
 * 
 * it contains the MergeRequest, List of MergeRequestNotes and the MergeRequestState, The List of Diffs
 */
public record MergeRequestInfo(MergeRequest mergeRequest, List<Note> mergeRequestNotes, List<Diff> diffs) {}