import { writable } from 'svelte/store';
import { browser } from '$app/environment';

export const data = writable({
	loggedIn: (browser && window.localStorage.getItem('jwt')) || '',
	role : (browser && window.localStorage.getItem('role')) || ''
});