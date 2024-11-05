import './assets/css/artist.css';

import { Link,useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';

function App() {

    const {token} = useParams(); 

    const [allArt, setAllArt] = useState([]);
    const [signature, setSignature] = useState([]);
      
    useEffect(() => {
        const allArtData = async() => {
        const allArtInfo = await axios.get(`http://192.168.0.7/api/artists/${token}/all-artworks`);
            setAllArt(allArtInfo.data);
        }
        
        const signatureData = async() => {
        const signatureInfo = await axios.get(`http://192.168.0.7/api/artists/${token}/signature-artworks`);
            setSignature(signatureInfo.data.artworks);
            console.log(signatureInfo.data.artworks);
        }
    
        allArtData();
        signatureData();
      
    }, []);

  return (
    <>
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px', width: 1140}}>
            <h2>작가 작품 더보기</h2> 
            <div>
                {signature.map((sign,index) => (
                    <div className="img_main"><Link to={`/artworks/${sign.token}`}><img src="https://via.placeholder.com/356x356" alt=""/></Link></div>
                ))}

                {allArt.map((all, index) => (
                    <div className="img_sub"><Link to={`/artworks/${all.token}`}><img src="https://via.placeholder.com/261x261" alt=""/></Link></div>
                ))}   
            </div>
        </div>
    </div>
    </>
  )
}

export default App
