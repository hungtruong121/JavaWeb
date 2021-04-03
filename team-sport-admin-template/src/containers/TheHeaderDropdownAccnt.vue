<template>
  <CDropdown
    inNav
    class="c-header-nav-items"
    placement="bottom-end"
    add-menu-classes="pt-0"
  >
    <template #toggler>
      <CHeaderNavLink>
        <div class="c-avatar">
          <img
            src="img/avatars/6.jpg"
            class="c-avatar-img "
          />
        </div>
      </CHeaderNavLink>
    </template>
    <CDropdownHeader tag="div" class="text-center" color="light">
      <strong>Account</strong>
    </CDropdownHeader>
    <CDropdownItem>
      <CIcon name="cil-shield-alt" /> Change password
    </CDropdownItem>
    <CDropdownItem @click="logout($event)">
      <CIcon name="cil-lock-locked" /> Logout
    </CDropdownItem>
  </CDropdown>
</template>

<script>
import axios from "../../http-common";

export default {
  name: 'TheHeaderDropdownAccnt',
  data () {
    return { 
      itemsCount: 42
    }
  },
  methods: {
    logout(e) {
      e.preventDefault();
      axios
        .get("/logout")
        .then((resp) => {
          let response = resp.data;
          if (response != null) {
            if (response.success == false) {
              return;
            }
            if ((response.success == true) && (response.data != null)) {
              localStorage.removeItem('username');
              localStorage.removeItem('userId');
              localStorage.removeItem('role');
              localStorage.removeItem('token');
              this.$router.push({ path: `/pages/login` });
            }
          }
        })
        .catch(function (resp) {
        });
    },
  },
}
</script>

<style scoped>
  .c-icon {
    margin-right: 0.3rem;
  }
</style>