// import router from './router'

// function isLogin () {
//   return true
// }

// const whiteList = ['/login']

// router.beforeEach((to, from, next) => {
//   if (isLogin()) {
//     if (to.path === '/login') {
//       next({ path: '/' })
//     } else {
//       next()
//     }
//   } else {
//     if (whiteList.indexOf(to.path) !== -1) {
//       next()
//     } else {
//       next({ path: '/login' })
//     }
//   }
// })
