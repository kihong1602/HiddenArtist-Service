import './assets/css/artist.css';
import { Link,useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {

const {token} = useParams(); 

const [artists, setArtist] = useState([]);
const [signature, setSignature] = useState([]);
  
useEffect(() => {
    const artistData = async() => {
    const artistInfo = await axios.get(`http://192.168.0.7/api/artists/${token}`);
        setArtist(artistInfo.data);
    }
    
    const signatureData = async() => {
    const signatureInfo = await axios.get(`http://192.168.0.7/api/artists/${token}/signature-artworks`);
        setSignature(signatureInfo.data.artworks);
        console.log(signatureInfo.data.artworks);
    }

    artistData();
    signatureData();
  
}, []);

  return (
    <>  

        <div style={{marginTop: '62px', marginBottom:'24px',display:'flex', flexDirection:'column', alignItems:'center'}}>
            <div style={{marginBottom:'62px'}}>
                <h2>작가 상세 페이지</h2> 
                <div>
                    <div id='profile'><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/261x261" /></div>
                    <div style={{display: 'inline-block', verticalAlign:'top', position:'relative', height:261}}>
                        <table id='artistInfo'>
                            <tbody>
                                <tr>
                                    <td>Name</td>
                                    <td>{artists.name}</td>
                                </tr>
                                <tr>
                                    <td>Summery</td>
                                    <td>{artists.summary}</td>
                                </tr>
                                <tr>
                                    <td>Birth</td>
                                    <td>{artists.birth}</td>
                                </tr>
                                <tr>
                                    <td>Genre</td>
                                    <td>{artists.genre}</td>
                                </tr>
                                <tr>
                                    <td>Connect</td>
                                    <td>email@email.com</td>
                                </tr>
                            </tbody>
                        </table>

                        <div style={{position:'absolute', bottom:0}}><button id='follow'>Follow</button></div>
                    </div>

                    <div style={{marginTop:'32px', marginBottom:'46px', width:1111}}>
                        <div id='descTitle'>Description</div>
                        <div>{artists.description}</div>
                    </div>
                    
                    <div style={{width: 71, height: 4, background:'#FAFFFF', marginBottom: 32}}></div>
                    <div style={{width:1140}}>
                        <div style={{textAlign:'right', marginBottom: 16, marginRight:24}}><button id='preview3D'>3D View</button></div>
                        <div>
                            {signature.map((sign, index) => (
                                <div className="img_art" key={index}><Link to={`/artworks/${sign.token}`}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                            ))}
                        </div>

                        <Link to='more'>
                            <div id='moreBtn' style={{width: '100%', textAlign:'center', fontSize:24}}>More&nbsp;
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none">
                                    <path d="M19.4032 13.2H0V10.8H19.4032L10.2892 1.686L12 0L24 12L12 24L10.2892 22.314L19.4032 13.2Z" fill="white"/>
                                </svg>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default App
