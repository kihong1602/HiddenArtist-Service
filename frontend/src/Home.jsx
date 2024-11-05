import './assets/css/home.css';

import { Link } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

function App() {

    const category = ['추천 작품', '인기 작가', 'New','현재 진행중인 전시', '오픈 예정 전시'];
    const exhIdx = ['1','2','3','4'];

    const [recommend, setRecommend] = useState([]);
    const [popular, setPopular] = useState([]);
    const [newArtist, setNewArtist] = useState([]);
    
    useEffect(() => {
        // 추천작품
        const recommendData = async() => {
        const recommendInfo = await axios.get(`http://192.168.0.7/api/artworks/recommend`);
            setRecommend(recommendInfo.data.artworks);
        }
        
        // 인기작가
        const popularData = async() => {
        const popularInfo = await axios.get(`http://192.168.0.7/api/artists/popular`);
            setPopular(popularInfo.data.artists);
        }

        // 신인작가
        const newArtistData = async() => {
        const newArtistInfo = await axios.get(`http://192.168.0.7/api/artists/new`);
            setNewArtist(newArtistInfo.data.artists);
        }

        recommendData();
        popularData();
        newArtistData();
    
    }, []);

    const artworksList = (state) => {
        switch(state) {
            case 0 : 
            return recommend.map((artworks, index) => (
                <Link to={`/artworks/${artworks.token}`}><div className="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div></Link>
            ));
            case 1 :
            return popular.map((popular, index) => (
                <Link to={`/artist/${popular.token}`}><div className="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div></Link>
            ));
            case 2 :
            return newArtist.map((newArtist, index) => (
                <Link to={`/artist/${newArtist.token}`}><div className="img_art"><img src="https://via.placeholder.com/356x356" alt=""/></div></Link>
            ));
            default: 
            return <><Link to={`/artworks/${state}`}><div className="img_art"><img src="https://via.placeholder.com/261x261" alt=""/></div></Link><Link to={`/artworks/${state}`}><div className="img_art"><img src="https://via.placeholder.com/261x261" alt=""/></div></Link><Link to={`/artworks/${state}`}><div className="img_art"><img src="https://via.placeholder.com/261x261" alt=""/></div></Link></>
            
        }
    }


  return (
    <>

    <div id="banner">
        <Swiper
        spaceBetween={50}
        slidesPerView={1}
        pagination={{ clickable: true }}
        >
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        <SwiperSlide><img src="https://via.placeholder.com/1920x550" alt="" /></SwiperSlide>
        </Swiper>
        
    </div>

    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        {category.map((homeTitle, index) => (
            <div style={{marginBottom:'62px'}}>
                 <h2>{index>2 ? <Link to='/exhibition'>{homeTitle}&nbsp;<img src='./image/right-chevron.svg'/></Link> : homeTitle}</h2>
                <div>
                    {index>2 ? <><div className="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div><div className="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div><div className="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div><div className="img_exh"><img src="https://via.placeholder.com/261x175" alt=""/></div></> : artworksList(index)}
                </div>
            </div>
        ))}
    </div>
    </>
  )
}

export default App
