import request from '@/utils/request'

// 查询关键字管理列表
export function listWord(query) {
  return request({
    url: '/robot/word/list',
    method: 'get',
    params: query
  })
}

export function listAll() {
  return request({
    url: '/robot/word/listAll',
    method: 'post'
  })
}

// 查询关键字管理详细
export function getWord(id) {
  return request({
    url: '/robot/word/' + id,
    method: 'get'
  })
}

// 新增关键字管理
export function addWord(data) {
  return request({
    url: '/robot/word',
    method: 'post',
    data: data
  })
}

// 修改关键字管理
export function updateWord(data) {
  return request({
    url: '/robot/word',
    method: 'put',
    data: data
  })
}

// 删除关键字管理
export function delWord(id) {
  return request({
    url: '/robot/word/' + id,
    method: 'delete'
  })
}
