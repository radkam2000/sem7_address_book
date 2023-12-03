<script>
	import { onMount } from 'svelte';
	import '../app.postcss';
	import { AppShell, AppBar, Toast } from '@skeletonlabs/skeleton';
	import { goto } from '$app/navigation';
	import { data } from '../store';

	import { initializeStores } from '@skeletonlabs/skeleton';
	initializeStores();
	import { getToastStore } from '@skeletonlabs/skeleton';

	onMount(() => {
		$data.jwt = localStorage.getItem('jwt');
		$data.role = localStorage.getItem('role');
	});
	const logout = () => {
		localStorage.clear();
		$data.jwt = '';
		$data.role = '';
		goto('/');
	};

	const toastStore = getToastStore();

	function show_toast(
		message,
		background = 'variant-filled-primary',
		timeout = 10000,
		autohide = true,
		hideDismiss = false
	) {
		const t = {
			message,
			timeout,
			background,
			autohide,
			hideDismiss
		};
		toastStore.trigger(t);
	}
</script>

<!-- App Shell -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
<Toast></Toast>
<AppShell>
	<svelte:fragment slot="header">
		<!-- App Bar -->
		<AppBar>
			<svelte:fragment slot="lead">
				<strong class="btn text-xl uppercase" on:click={() => goto('/')}>Book.io</strong>
			</svelte:fragment>
			<svelte:fragment slot="trail">
				{#if $data.jwt}
					<button
						class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mb-8 w-full"
						on:click={logout}
					>
						logout
					</button>
				{/if}
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<!-- Page Route Content -->
	<slot />
</AppShell>
