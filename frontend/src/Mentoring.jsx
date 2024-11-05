import './assets/css/mentoring.css';
import { Link } from 'react-router-dom';

function App() {

  const idx = ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15'];

  return (
    <>
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px', width: 1140}}>
            <div style={{display:'flex', justifyContent:'space-between', alignItems:'center'}}>
              <h2>멘토링</h2>
              <div>
                <select name="sort" id="sort">
                  <option value="인기순">인기순</option>
                  <option value="최신순">최신순</option>
                </select>
              </div>
            </div>
            <div style={{display:'flex', flexWrap:'wrap'}}>
              {idx.map((index) => (
                <Link to={index}>
                  <div style={{display:'flex', marginBottom:24}}>
                    <div class="img_mentor"><img src="https://via.placeholder.com/261x307" alt=""/></div>
                    <div style={{width:261, marginRight:24 ,position:'relative'}}>
                      <div class='subTitle' style={{marginBottom:8}}>현대미술</div>
                      <div class='mainTitle' style={{marginBottom:8}}>멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 멘토링 타이틀 </div>
                      <div style={{marginBottom:16}}><span class='star'>★★★★★</span>&nbsp;<span class='subTitle'>(46)</span></div>
                      <div class='subTitle'>경력</div>
                      <div class='subTitle'>소속</div>
                      <div style={{position:'absolute', bottom:0}}>
                        <div class='subTitle' style={{marginBottom:8}}>마감날짜</div>
                        <div class='dDay' >남은 날짜</div>
                      </div>
                    </div>
                  </div>
                </Link>
              ))}
            </div>
        </div>
    </div>
    </>
  )
}

export default App
