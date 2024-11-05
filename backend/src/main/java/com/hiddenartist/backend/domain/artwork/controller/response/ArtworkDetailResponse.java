package com.hiddenartist.backend.domain.artwork.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hiddenartist.backend.domain.artist.persistence.Artist;
import com.hiddenartist.backend.domain.artwork.persistence.Artwork;
import com.hiddenartist.backend.domain.genre.persistence.Genre;
import com.hiddenartist.backend.global.type.EntityToken;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ArtworkDetailResponse extends ArtworkResponse {

  private List<String> genres;
  private Double width;
  private Double height;
  private Double depth;
  private List<ArtistInfo> artists;
  private String medium;
  @JsonProperty("production_year")
  private Integer productionYear;
  private String description;

  public ArtworkDetailResponse(String name, String token, String image, List<String> genres, Double width,
      Double height,
      Double depth, List<ArtistInfo> artists, String medium, LocalDate productionYear, String description) {
    super(name, token, image);
    this.genres = genres;
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.artists = artists;
    this.medium = medium;
    this.productionYear = productionYear.getYear();
    this.description = description;
  }

  public static ArtworkDetailResponse create(Artwork artwork, List<Artist> artists, List<Genre> genres) {
    List<String> genreNames = genres.stream().map(Genre::getName).toList();
    List<ArtistInfo> artistInfos = artists.stream().map(ArtistInfo::create).toList();
    return new ArtworkDetailResponse(
        artwork.getName(),
        artwork.getToken(),
        artwork.getImage(),
        genreNames,
        artwork.getWidth(),
        artwork.getHeight(),
        artwork.getDepth(),
        artistInfos,
        artwork.getArtworkMedium().getTypeName(),
        artwork.getProductionYear(),
        artwork.getDescription()
    );
  }

  public record ArtistInfo(
      String name,
      String token
  ) {

    public static ArtistInfo create(Artist artist) {
      return new ArtistInfo(artist.getName(), EntityToken.ARTIST.extractToken(artist.getToken()));
    }
  }
}
