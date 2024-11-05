start transaction;
create fulltext index artist_name_idx on artist (name) with parser ngram;
create fulltext index artwork_name_idx on artwork (name) with parser ngram;
create fulltext index genre_name_idx on genre (name) with parser ngram;
create fulltext index exhibition_name_idx on exhibition (name) with parser ngram;
create fulltext index mentoring_name_idx on mentoring (name) with parser ngram;
commit;