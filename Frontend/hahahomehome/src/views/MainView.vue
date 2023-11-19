<script setup>
import BoardNotice from '../components/board/BoardNotice.vue'
import BoardNews from '../components/board/BoardNews.vue'
import TheHeading from '../components/common/TheHeading.vue'
import TheWordCloud from '../components/info/TheWordCloud.vue'


</script>

<template>
  <TheHeading />
  <div>
    
    <div class="img-box">
      <div class="main-img">
        <div class="container">
        <div class="typewriter">Welcome to Go!집사</div>
      </div>
      </div>
      
    </div>
    <div class="contents">
      
      <BoardNotice />
      <BoardNews />
    </div>
    <TheWordCloud />

  </div>
</template>

<style scoped lang="scss">
.contents {
  display: flex;
  justify-content: space-around;
  align-items: center; /* 가운데 정렬을 위해 추가 */
}

.img-box {
  .main-img {
    height: 100vh;
    width: 100vw;
    background-image: url('../assets/img/buildings.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    position: relative;
  }
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-size: clamp(8rem, 2.8vw, 5rem);
  font-family: Raleway;
  font-weight: bold;
  color:white
}

.typewriter {
	width: 21.5ch;
	white-space: nowrap;
	overflow: hidden;
	border-right: 4px solid white;
	animation: cursor 1s step-start infinite, 
    text 5s steps(18) alternate infinite;
}

@keyframes cursor {
	0%, 100% { 
    border-color:white; 
  }
}

@keyframes text {
	0% { 
    width: 0; 
  }
	100% { 
    width: 21.5ch; 
  }
}
</style>
