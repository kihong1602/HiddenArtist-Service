import './assets/css/artworks.css';

import { Link,useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';

function App() {

    const idx = [1,2,3];

    const {token} = useParams(); 

    const [artworks, setArtworks] = useState([]);
    const [artists, setArtists] = useState([]);

    useEffect(() => {
        const artworksData = async () => {
            try {
                const res = await axios.get(`http://192.168.0.7/api/artworks/${token}`);
                setArtworks(res.data);
            } catch (error) {
                console.error("Error fetching artworks:", error);
            }
        };

        artworksData();
    }, []);  

    useEffect(() => {
        if (artworks.artists && artworks.artists.length > 0) {
            const artistToken = artworks.artists[0].token;

            const artistsData = async () => {
                try {
                    const res = await axios.get(`http://192.168.0.7/api/artists/${artistToken}`);
                    setArtists(res.data);
                } catch (error) {
                    console.error("Error fetching artists:", error);
                }
            };

            artistsData();
        }
    }, [artworks]);

    

  return (
    <>  

        <div style={{marginTop: '62px', marginBottom:'24px',display:'flex', flexDirection:'column', alignItems:'center'}}>
            <div style={{marginBottom:'62px'}}>
                <h2>작품 상세 페이지</h2> 
                <div>
                    <div id='artworkImg'><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/546x546" /></div>
                    <div style={{display: 'inline-block', verticalAlign:'top', position:'relative', height:261}}>
                        <table id='artworksInfo'>
                            <tbody>
                                <tr>
                                    <td>Title</td>
                                    <td>{artworks.name}</td>
                                </tr>
                                <tr>
                                    <td>Author</td>
                                    <td>{artists ? artists.name : ''}</td> 
                                </tr>
                                <tr>
                                    <td>Genre</td>
                                    <td>{artworks.genres+' '}</td>
                                </tr>
                                <tr>
                                    <td>Year</td>
                                    <td>{artworks.production_year}</td>
                                </tr>
                                <tr>
                                    <td>Media</td>
                                    <td>{artworks.medium}</td>
                                </tr>
                                <tr>
                                    <td>Size</td>
                                    <td>{`${artworks.width} X ${artworks.height}`}</td>
                                </tr>
                                <tr>
                                    <td>Description</td>
                                    <td>{artworks.description}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div style={{border: '1px solid #FAFFFF', marginTop:62, width:1111, height:199, display:'flex', marginBottom:64}}>
                        
                        <div style={{display:'flex', width:'50%', alignItems:'center'}}>
                            <div id='profile2' style={{height: '100%', alignItems:'center',marginLeft:32, marginRight:32}}>
                                <img style={{width: 150, height: 150}} src="https://via.placeholder.com/150x150" />
                            </div>
                            <div style={{display:'flex', flexDirection:'column',marginRight:64}}>
                                <div>{artists.name}</div>
                                 <div>{artists.birth}</div>
                                <div>email@email.com</div>
                            </div>
                            <div><button id='follow'>Follow</button></div>
                        </div>
                        <div style={{width:'50%'}}><Link to={`/artist/${artists ? artists.token : ""}`}><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/558x199" /></Link></div>
                    </div>

                    <div style={{width:1140}}>
                        <h2>작가의 다른 작품</h2>
                        <div>
                            {idx.map((index) => (
                                <div className="img_art"><Link to={'/artworks/'+index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>
                    </div>

                    <div style={{width:1140}}>
                        <h2>연관 다른 작품</h2>
                        <div>
                            {idx.map((index) => (
                                <div className="img_art"><Link to={'/artworks/'+index}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default App
