<script setup>
import { onMounted, ref, watch } from 'vue'
import * as d3 from 'd3'
import cloud from 'd3-cloud'
import { getCloudData } from '@/api/wordcloud'

// 워드클라우드 레이아웃 생성
let layout
// 반응형 하면 안 된다
let nouns

onMounted(() => {
  const width = 1000
  const height = 500

  // Function to generate a random color
  function getRandomColor() {
    const letters = '0123456789ABCDEF'
    let color = '#'
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)]
    }
    return color
  }

  // Array of predefined light purple colors
  const lightPurpleColors = [
    '#B19CD9',
    '#B39EB5',
    '#C09DD9',
    '#C8A2C8',
    '#DBA9DB',
    '#E6A9E6',
    '#F0E6F5',
    '#E1D5E7'
  ]

  // Function to generate a random color from the predefined array
  function getRandomLightPurpleColor() {
    return lightPurpleColors[Math.floor(Math.random() * lightPurpleColors.length)]
  }

  // Array of predefined dark purple colors
  const darkPurpleColors = ['#b126bc', '#a139c7', '#7e03a3', '#805ad8', '#d485d4', '#5516c3']

  // Function to generate a random color from the predefined array
  function getRandomDarkPurpleColor() {
    return darkPurpleColors[Math.floor(Math.random() * darkPurpleColors.length)]
  }

  const draw = () => {
    console.log('그리기!')
    const container = d3.select(document.getElementById('wordcloud'))

    container
      .append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', `translate(${width / 2},${height / 2})`)
      .selectAll('text')
      .data(nouns)
      .enter()
      .append('text')
      .style('font-size', (d) => `${d.size}px`)
      .style('fill', () => getRandomDarkPurpleColor())
      //   .style('fill', 'steelblue')
      .attr('text-anchor', 'middle')
      .attr('transform', function (d) {
        return 'translate(' + [d.x, d.y] + ')rotate(' + d.rotate + ')'
      })
      .text((d) => d.text)
  }

  // Local API 워드클라우드 명사들 가져오기
  getCloudData(
    ({ data }) => {
      console.log('data == ', data)
      nouns = data
      console.log('nouns.value == ', nouns)

      layout = cloud()
        .size([width, height])
        .words(nouns)
        .padding(5)
        .rotate(() => (Math.random() > 0.5 ? 0 : 0)) // 랜덤한 회전
        .font('Impact')
        .fontSize((d) => d.size)
        .on('end', draw)

      layout.start()
    },
    (error) => {
      console.log(error)
    }
  )
})
</script>

<template>
  <div id="wordcloud"></div>
</template>

<style scoped>
#wordcloud {
  padding: 2rem;
}
</style>
