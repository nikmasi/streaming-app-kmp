export type ContentType = 'MOVIE' | 'TV_SHOW';

export type PreferenceStatus =
  | 'LIKED'
  | 'DISLIKED'
  | 'WATCH_LATER';

export interface UserContentPreference {
  id?: string;
  userId: string;
  contentId: string;
  contentType: ContentType;
  status: PreferenceStatus;
}

export interface PreferenceRequest {
  contentId: string;
  contentType: ContentType;
  status: PreferenceStatus;
}