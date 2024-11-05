import { Route, Routes } from "react-router-dom";
import Header from './Header.jsx'
import Home from './Home.jsx'
import Artist from './Artist.jsx'
import ArtistDetail from './ArtistDetail.jsx'
import ArtistArtMore from './ArtistArtMore.jsx'
import Artworks from './Artworks.jsx'
import ArtworksDetail from './ArtworksDetail.jsx'
import Exhibition from './Exhibition.jsx'
import ExhDetail from './ExhDetail.jsx'
import Mentoring from './Mentoring.jsx'

function App() {

  return (
    <div>
      
      <Routes>
        <Route element={<Header />}>
          <Route path="" element={<Home />} />
          <Route path="artist" element={<Artist />} />
          <Route path="artist/:token" element={<ArtistDetail />} />
          <Route path="artist/:token/more" element={<ArtistArtMore />} />
          <Route path="artworks" element={<Artworks />} />
          <Route path="artworks/:token" element={<ArtworksDetail />} />
          <Route path="exhibition" element={<Exhibition />} />
          <Route path="exhibition/:token" element={<ExhDetail />} />
          <Route path="mentoring" element={<Mentoring />} />
        </Route>
      </Routes>
    </div>
  );
}
export default App;