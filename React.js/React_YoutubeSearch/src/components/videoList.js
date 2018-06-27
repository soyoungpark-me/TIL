import React from 'react';
import VideoListItem from './videoListItem';

const VideoList = (props) => {
  const videoItems = props.videos.map((video) => {
    return (
      <VideoListItem 
        onVideoClick={props.onVideoSelect}
        key={video.etag}
        video={video}/>
    )
  });
  // 리액트에선 배열 아이템을 렌더링할 때마다 리스트를 만드는 것이라고 가정함.
  // 리액트는 리스트를 만드는 프로세스를 최대한 최적화해서 만들 것!
  // 특정 하나를 업데이트하려면, 아이디를 부여해서 구별해야 함. 그게 아니면 리스트를 몽땅 날렸다가 다시 렌더링해야 함.
  // 때문에 리스트를 위한 키는 명료해야 한다. 숫자일 필요는 없다.

  return(
    <ul className="col-md-4 list-group">
      {videoItems}
    </ul>
  );
};

export default VideoList;