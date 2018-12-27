import React from 'react';

const VideoListItem = ({video, onVideoClick}) => {
  // const video = props.video;
  // 인수로 {video}를 받는 것은 위의 구문과 똑같다. 변수명과 props 명이 같을 경우에 쓸 수 있다. (ES6 문법)

  const imgUrl = video.snippet.thumbnails.default.url;
  return (
    <li onClick={() => onVideoClick(video)} className="list-group-item">
      <div className="video-list media">
        <div className="media-lift">
          <img className="media-object" src={imgUrl}/>
        </div>
        <div className="media-body">
          <div className="media-heading">
            {video.snippet.title}
          </div>
        </div>
      </div>
    </li>
  );
};

export default VideoListItem;