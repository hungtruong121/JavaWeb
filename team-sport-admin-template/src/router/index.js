import Vue from 'vue'
import Router from 'vue-router'

// Containers
const TheContainer = () => import('@/containers/TheContainer')

// Views
const Dashboard = () => import('@/views/Dashboard')

// Views - Pages
const Page404 = () => import('@/views/pages/Page404')
const Page500 = () => import('@/views/pages/Page500')
const Login = () => import('@/views/pages/Login')
const Register = () => import('@/views/pages/Register')
const ChangePass = () => import('@/views/pages/ChangePass')

//promotion
const CreatePromotion = ()=>import('@/views/promotions/CreatePromotion')
const ListPromotions = () => import('@/views/promotions/ListPromotions')

//Payment 
const Payment =() =>import('@/views/payment/Payment')

// Users
const CreateUser = () => import('@/views/users/CreateUser')
const ListUsers = () => import('@/views/users/ListUsers')
const UserDetails = () => import('@/views/users/UserDetails')
const Users = () => import('@/views/users/Users')
const User = () => import('@/views/users/User')

// Sport
const ListSport = () => import('@/views/sport/ListSport')
const DefinitionSport = () => import('@/views/sport/SportDefinition')

// Ranks
const CreateRank = () => import('@/views/ranks/CreateRank')
const ListRanks = () => import('@/views/ranks/ListRanks')
const RankDetails = () => import('@/views/ranks/RankDetails')
// Team
const AddTeam = () => import('@/views/team/AddTeam')
const ListTeam = () => import('@/views/team/ListTeam')
const TeamUpdate = () => import('@/views/team/TeamUpdate')

Vue.use(Router)

let router = new Router({
  mode: 'hash', // https://router.vuejs.org/api/#mode
  linkActiveClass: 'active',
  scrollBehavior: () => ({ y: 0 }),
  routes: configRoutes(),
})

function configRoutes () {
  return [
    {
      path: '/',
      redirect: '/dashboard',
      name: 'Home',
      component: TheContainer,
      children: [
        {
          path: 'dashboard',
          name: 'Dashboard',
          component: Dashboard,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: 'sport/listsport',
          name: 'List Sport',
          component: ListSport,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: 'sport/definesport',
          name: 'Definition Sport',
          component: DefinitionSport,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: "/sport/detail/:id",
          name: "Update Sport",
          component: DefinitionSport,
          meta: {
            requiresAuth: true
          }
        },

         //promotions
         {
          path: 'promotions/createpromotion',
          name: 'Create Promotion',
          component: CreatePromotion
        },
        {
          path: 'promotions/listpromotions',
          name: 'List Promotions',
          component: ListPromotions
        },

        //Payment
        {
          path: 'payment/payment',
          name: 'Payment History',
          component: Payment
        },

        {
          path: 'user/create',
          name: 'Create User',
          component: CreateUser,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: 'user/listusers',
          name: 'List Users',
          component: ListUsers,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: "/user/detail/:id",
          name: "User Details",
          component: UserDetails,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: 'team/add',
          name: 'Add Team',
          component: AddTeam,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: 'team/listteams',
          name: 'List Teams',
          component: ListTeam,
          meta: {
            requiresAuth: true
          }
        },
        {
          path: "/team/detail/:id",
          name: "Team Update",
          component: TeamUpdate,
          meta: {
            requiresAuth: true
          }
        },
        
        {
          path: 'user',
          meta: {
            label: 'Users'
          },
          component: {
            render(c) {
              return c('router-view')
            }
          },
          children: [
            {
              path: '',
              name: 'Users',
              component: Users,
              meta: {
                requiresAuth: true
              }
            },
            {
              path: ':id',
              meta: {
                label: 'User Details'
              },
              name: 'User',
              component: User,
              meta: {
                requiresAuth: true
              }
            }
          ]
        },
        {
          path: 'rank/create',
          name: 'Create Rank',
          component: CreateRank
        },
        {
          path: 'rank/listranks',
          name: 'List Ranks',
          component: ListRanks
        },
        {
          path: 'rank/detail/:id',
          name: 'Rank Details',
          component: RankDetails
        },
      ]
    },
    {
      path: '/pages',
      redirect: '/pages/404',
      name: 'Pages',
      component: {
        render (c) { return c('router-view') }
      },
      children: [
        {
          path: '404',
          name: 'Page404',
          component: Page404,
          meta: {
              guest: true
          }
        },
        {
          path: '500',
          name: 'Page500',
          component: Page500,
          meta: {
            guest: true
          }
        },
        {
          path: "changepass",
          name: "Changepassword",
          component: ChangePass
        },
        {
          path: 'login',
          name: 'Login',
          component: Login,
          meta: {
            guest: true
          }
        },
        {
          path: 'register',
          name: 'Register',
          component: Register,
          meta: {
            guest: true
          }
        }
      ]
   
      
    }
  ]
}

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)) {
      if (localStorage.getItem('token') == null) {
          next({
              path: '/pages/login',
              params: { nextUrl: to.fullPath }
          })
      } else {
        let userAdmin = localStorage.getItem('role');
        if(to.matched.some(record => record.meta.is_admin)) {
          console.log("4")
          if(userAdmin == 2){//role id admin is 2
            next({ name: 'dashboard'})
          }
          else{
            next({
              path: '/pages/login',
              params: { nextUrl: to.fullPath }
            })
          }
        }else {
          next()
        }
      }
  } else if(to.matched.some(record => record.meta.guest)) {
      if(localStorage.getItem('token') == null){
        next()
      }
      else{
        next({ name: 'dashboard'})
      }
  }else {
      next()
  }
})
export default router
