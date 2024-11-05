import './assets/css/artist.css';
import { Link, useSearchParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Pagination from 'react-bootstrap/Pagination';
//import "bootstrap/dist/css/bootstrap.min.css";

function App() {

  const [data, setData] = useState([]);
  const [ query, setQuery ] = useSearchParams();
  const page = query.get('p');
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(`http://192.168.0.7/api/artists?p=${page}`);
        setData({
          content: res.data.artists.content,
          page: res.data.artists.page 
        });
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [page]);

  let totalPages = 1; 

  if (data.page && data.page.totalPages) {
    totalPages = data.page.totalPages;
  }

  
  let active = 1;
  let items = [];
  for (let number = 1; number <= totalPages; number++) {
    items.push(
      
      <Pagination.Item key={number} active={number === active}>
        <Link to={`?p=${number}`}>{number}</Link>
      </Pagination.Item>,
    );
  }

  let paginationBasic = "";
  if(totalPages>1) {
    paginationBasic = (
      <div>
        <Pagination>
          <Pagination.Item>
            <Link to={`?p=1`}>처음</Link>
          </Pagination.Item>
          <Pagination.Item>
            <Link to={`?p=${page-1}`}>이전</Link>
          </Pagination.Item>
          
          {items}

          <Pagination.Item>
            <Link to={`?p=${page+1}`}>다음</Link>
          </Pagination.Item>
          <Pagination.Item>
            <Link to={`?p=${totalPages}`}>끝</Link>
          </Pagination.Item>
      </Pagination>
      </div>
    );
  }

  

  return (
    <>
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px', width: 1140}}>
            <h2>작가</h2> 
            <div>
               {data.content ? data.content.map((key) => (
                  <div className="img_art"><Link to={key.token}><img src='https://via.placeholder.com/356x356' alt=""/></Link></div>
              )) : ""} 
            </div>

          {/* {paginationBasic} */}

        </div>
    </div>

    </>
  )
}

export default App
