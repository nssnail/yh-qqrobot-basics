import request from '@/utils/request'

// 查询英语长句难句列表
export function listSentence(query) {
  return request({
    url: '/robot/sentence/list',
    method: 'get',
    params: query
  })
}

// 查询英语长句难句详细
export function getSentence(id) {
  return request({
    url: '/robot/sentence/' + id,
    method: 'get'
  })
}

// 新增英语长句难句
export function addSentence(data) {
  return request({
    url: '/robot/sentence',
    method: 'post',
    data: data
  })
}

// 修改英语长句难句
export function updateSentence(data) {
  return request({
    url: '/robot/sentence',
    method: 'put',
    data: data
  })
}

// 删除英语长句难句
export function delSentence(id) {
  return request({
    url: '/robot/sentence/' + id,
    method: 'delete'
  })
}
