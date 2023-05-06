import request from '@/utils/request'

// 查询通知公告列表
export function listNotice(query) {
  return request({
    url: '/robot/notice/list',
    method: 'get',
    params: query
  })
}

// 查询通知公告详细
export function getNotice(id) {
  return request({
    url: '/robot/notice/' + id,
    method: 'get'
  })
}

// 新增通知公告
export function addNotice(data) {
  return request({
    url: '/robot/notice',
    method: 'post',
    data: data
  })
}

// 修改通知公告
export function updateNotice(data) {
  return request({
    url: '/robot/notice',
    method: 'put',
    data: data
  })
}

// 删除通知公告
export function delNotice(id) {
  return request({
    url: '/robot/notice/' + id,
    method: 'delete'
  })
}


// 通知
export function noticeAll(id) {
  return request({
    url: '/robot/notice/noticeAll/' + id,
    method: 'post'
  })
}

