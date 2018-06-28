import React from 'react';

const VideoDetail = ({video}) => {
  // NULL props 핸들링
  // 렌더링을 시도하기 전에 비디오가 props에 잘 주어졌는지를 먼저 확인한다.

  if(!video) {
    return <div>Loading...</div>
  }

  const videoId = video.id.videoId;
  // const url = 'http://www.youtube.com/embed/' + videoId;
  const url = `http://www.youtube.com/embed/${videoId}` //위의 내용과 같은 ES6 문법

  return (
    <div className="video-detail col-md-8">
      <div className="embed-responsive embed-responsive-16by9">
        <iframe className="embed-responsive-item" src={url}></iframe>
      </div>
      <div className="details">
        <div>{video.snippet.title}</div>
        <div>{video.snippet.description}</div>
      </div>
    </div>
  )
};

export default VideoDetail;