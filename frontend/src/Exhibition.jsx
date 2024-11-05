import './assets/css/exhibition.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import { Link } from 'react-router-dom';


function App() {

  const titleArr = ['진행중인 전시회', '오픈 예정 전시회', '마감된 전시회'];
  const testArr = ['1', '2', '3', '4','1', '2', '3', '4'];        

  return (
    <>
    <div style={{marginTop:'62px', marginBottom:'24px', display: 'flex', flexDirection:'column', alignItems:'center'}}>
      {titleArr.map((category, index) => (
        <div style={{width:1111}}>
          <h2>{category}</h2>
          <Swiper
          spaceBetween={24}
          slidesPerView={4}
          slidesPerGroup={1}
          pagination={{ clickable: true }}
          >
          {testArr.map((idx) => (
              <SwiperSlide>
                <Link to={idx}>
                  <div className={"exh_"+index} style={{display: 'inline-block'}}>
                    <div className="img_exh"><img src="https://pimg.mk.co.kr/meet/neds/2017/09/image_readmed_2017_601694_15047650883020840.jpg" alt=""/></div>
                    <div className='exhTitle'>전시회 명</div>
                    <div className='exhPeriod'>2024.09.20~2024.10.20</div>
                  </div>
                </Link>
              </SwiperSlide>
          ))}
          </Swiper>
        </div>
      ))}
      </div>
    </>
  )
}

export default App
