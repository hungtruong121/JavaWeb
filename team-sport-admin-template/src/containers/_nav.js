export default [
  {
    _name: 'CSidebarNav',
    _children: [
      {
        _name: 'CSidebarNavItem',
        name: 'Dashboard',
        to: '/dashboard',
        icon: 'cil-speedometer',
        badge: {
          color: 'primary',
          text: 'NEW'
        }
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'SPORT',
        route: '/sport',
        icon: 'cil-user',
        items: [
          {
            name: 'Define Sport',
            to: '/sport/definesport'
          },
          {
            name: 'List Sports',
            to: '/sport/listsport'
          }
        ]
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'USER',
        route: '/user',
        icon: 'cil-user',
        items: [
          {
            name: 'Create User',
            to: '/user/create'
          },
          {
            name: 'List Users',
            to: '/user/listusers'
          }
        ]
      },
      
      //Promotion
      {
        _name: 'CSidebarNavDropdown',
        name: 'PROMOTIONS',
        route: '/promotions',
        icon: 'cil-user',
        items: [
          {
            name: 'Create Promotion',
            to: '/promotions/createpromotion'
          },
          {
            name: 'List Promotions',
            to: '/promotions/listpromotions'
          },
        ]
      },

      //Payment
      {
        _name: 'CSidebarNavDropdown',
        name: 'PAYMENT',
        route: '/payment',
        icon: 'cil-user',
        items: [
          {
            name: 'Payment History',
            to: '/payment/payment'
          }
        ]
      },

      {
        _name: 'CSidebarNavDropdown',
        name: 'RANK',
        route: '/rank',
        icon: 'cil-user',
        items: [
          {
            name: 'Create Rank',
            to: '/rank/create'
          },
          {
            name: 'List Ranks',
            to: '/rank/listranks'
          },
        ]
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'TEAM',
        route: '/team',
        icon: 'cil-user',
        items: [
          {
            name: 'Add Team',
            to: '/team/add'
          },
          {
            name: 'List Teams',
            to: '/team/listteams'
          }
        ]
      },
      {
        _name: 'CSidebarNavTitle',
        _children: ['FUNCTION']
      },
      {
        _name: 'CSidebarNavDropdown',
        name: 'Pages',
        route: '/pages',
        icon: 'cil-star',
        items: [
          {
            name: 'Change Password',
            to: '/pages/changepass'
          }
        ]
      },
    ]
  }
]