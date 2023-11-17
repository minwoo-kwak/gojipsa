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
  const height = 1000

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
      .style('fill', (d) => (d.sentiment === 'positive' ? 'green' : 'red'))
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
  <h1>Word Cloud</h1>
  <div id="wordcloud"></div>
</template>

<style scoped></style>
