import { Link, Outlet } from 'react-router-dom';
import '/src/assets/css/header.css';

function Header() {
  
  return (
    <>
      <div style={{display:'flex',margin: '15px auto', justifyContent:'center'}}>
        <div id="logo"><Link to='/'><img src="/image/logo_fix_w.png" alt=""/></Link></div>

        <div id="search">
            <input type='text' placeholder='Search by artist, gallery, style, theme, tag, etc...'/>        
            <div style={{display:'inline-block'}}>
              <img style={{width: '20px', height: '20px'}} src='/image/search_icon.svg'/>
            </div>
        </div>


        <button id="loginBtn">Login</button>
        <button id="signupBtn">Sign Up</button>
    </div>

    <div id="menu">
        <div><Link to='artist?p=1'>작가</Link></div>
        {/* <div><Link to='artworks'>작품</Link></div> */}
        <div><Link to='exhibition'>전시회</Link></div>
        <div><Link to='mentoring'>멘토링</Link></div>
        
    </div>
    
    <div style={{width: '100%', height: 1, background: 'gray'}}></div>

    <Outlet/>
    </>
  )
}

export default Header;
