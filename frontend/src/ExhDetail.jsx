import './assets/css/exhibition.css'

function App() {

  return (
    <>  

        <div style={{marginTop: '62px', marginBottom:'24px',display:'flex', flexDirection:'column', alignItems:'center'}}>
            <div style={{marginBottom:'62px'}}>
                <h2>전시회 상세페이지</h2> 
                <div>
                    <div style={{display: 'inline-block', width:541, height:363, marginRight:32}}><img style={{width: '100%', height: '100%', paddingRight:'32px'}} src="https://via.placeholder.com/541x363" /></div>
                    <div style={{display: 'inline-block', verticalAlign:'top', position:'relative', height:363}}>
                        <table id='exhInfo'>
                            <tbody>
                                <tr>
                                    <td>Title</td>
                                    <td>전시회 이름</td>
                                </tr>
                                <tr>
                                    <td>Period</td>
                                    <td>2024.09.20~2024.10.20</td>
                                </tr>
                                <tr>
                                    <td>Operating hours</td>
                                    <td>AM 09:00~PM 06:00</td>
                                </tr>
                                <tr>
                                    <td>Closed days</td>
                                    <td>매주 월요일</td>
                                </tr>
                                <tr>
                                    <td>Place</td>
                                    <td>국립현대미술관</td>
                                </tr>
                                <tr>
                                    <td>Admission Fees</td>
                                    <td>15,000원</td>
                                </tr>
                                <tr>
                                    <td>Etc</td>
                                    <td>email@email.com</td>
                                </tr>
                            </tbody>
                        </table>

                        <div style={{position:'absolute', bottom:0}}><button id='preview3D'>전시회 미리보기</button></div>
                    </div>

                    <div style={{marginTop:'32px', marginBottom:'46px', width:1111}}>
                        <div style={{marginBottom:'16px'}}>Description</div>
                        <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                            incididunt ut labore et dolore magna aliqua. Nisl tincidunt eget nullam non. 
                            Quis hendrerit dolor magna eget est lorem ipsum dolor sit. Volutpat odio facilisis 
                            mauris sit amet massa. Commodo odio aenean sed adipiscing diam donec adipiscing tristique. 
                            Mi eget mauris pharetra et. Non tellus orci ac auctor augue. Elit at imperdiet dui 
                            accumsan sit. Ornare arcu dui vivamus arcu felis. Egestas integer eget aliquet nibh 
                            praesent. In hac habitasse platea dictumst quisque sagittis purus. Pulvinar elementum 
                            integer enim neque volutpat ac.</div>
                    </div>

                    <div>
                        <div style={{marginBottom:'16px'}}>Map</div>
                        <div style={{width:1111, height:487}}><img style={{width: '100%', height: '100%'}} src="https://via.placeholder.com/1111x487" /></div>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}

export default App
