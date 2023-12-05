<script>
	import { onMount } from 'svelte';
	import { data } from '../../store';
	import { getToastStore } from '@skeletonlabs/skeleton';

	const inputs = ['name', 'surname', 'address', 'buildingNumber', 'flatNumber', 'phoneNumber'];
	const columnTitles = [
		'Id',
		'Name',
		'Surname',
		'Address',
		'Building Number',
		'Flat Number',
		'Phone Number'
	];

	let editedContact = {
		id: '',
		name: '',
		surname: '',
		address: '',
		buildingNumber: '',
		flatNumber: '',
		phoneNumber: ''
	};

	let record = {
		name: '',
		surname: '',
		address: '',
		buildingNumber: '',
		flatNumber: '',
		phoneNumber: ''
	};

	const token = $data.jwt;

	const URL = 'http://localhost:8000/api/contacts';
	let contactData = [];
	const getData = async () => {
		contactData = await (
			await fetch(URL, { method: 'GET', headers: { Authorization: 'Bearer ' + token } })
		).json();
	};

	onMount(async () => getData());

	async function insertRecord() {
		console.log(record);
		const res = await fetch(URL, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + token
			},
			body: await JSON.stringify(record)
		});
		var resBody = await res.json();
		if (res.status == 400 && resBody.isCustom == 'true') {
			var msg = res.status + ': ';
			delete resBody.isCustom;
			for (var key in resBody) {
				msg += resBody[key] + ', ';
			}
			show_toast(msg);
		} else if (res.status == 400) {
			show_toast(res.status + ' Type error');
		}
		if (res.status == 200) {
			show_toast(res.status + ' Success', 'variant-filled-secondary');
		}
		await getData();
	}

	async function submitUpdate() {
		console.log(record);
		const res = await fetch(URL + '/' + editedContact.id, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json',
				Authorization: 'Bearer ' + token
			},
			body: await JSON.stringify(editedContact)
		});
		var resBody = await res.json();
		if (res.status == 400 && resBody.isCustom == 'true') {
			var msg = res.status + ': ';
			delete resBody.isCustom;
			for (var key in resBody) {
				msg += resBody[key] + ', ';
			}
			show_toast(msg);
		} else if (res.status == 400) {
			show_toast(res.status + ' Type error');
		}
		if (res.status == 200) {
			show_toast(res.status + ' Success', 'variant-filled-secondary');
		}
		await getData();
	}

	async function delet(id) {
		const res = await fetch(URL + '/' + id, {
			method: 'DELETE',
			Authorization: 'Bearer ' + token
		});
		if (res.status == 404) {
			show_toast('404 Not Found');
		}
		await getData();
	}

	const toastStore = getToastStore();

	function show_toast(
		message,
		background = 'variant-filled-primary',
		timeout = 5000,
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

<!-- container -->
<div class="container">
	{#if $data.role != 'USER'}
		<div class="page-header">
			<h4>Create Contact</h4>
		</div>
		<div class="row">
			<form class="form-group" id="product-form">
				{#each inputs as item}
					<div class="col-md-2">
						<div class="form-group">
							<input
								type="text"
								bind:value={record[item]}
								name={item}
								class="form-control"
								placeholder="Enter {item}"
								id={item}
							/>
						</div>
					</div>
				{/each}

				<div class="col-md-2">
					<div class="form-group">
						<input
							type="button"
							name="insert"
							id="insert"
							on:click={insertRecord}
							class="btn btn-primary btn-lg"
							value="Insert Contact"
						/>
					</div>
				</div>
			</form>
		</div>
	{/if}
	<div class="page-header">
		<h4>Address Book</h4>
	</div>
	<div class="row">
		<div class="table-responsive">
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						{#each columnTitles as item}
							<th width="10%">{item}</th>
						{/each}
						{#if $data.role != 'USER'}
							<th width="8%"></th>
						{/if}
					</tr>
				</thead>
				<tbody id="table-data">
					{#if contactData.length != 0}
						{#each contactData as item}
							<tr>
								<th width="10%">{item['id']}</th>
								{#each inputs as field}
									<th width="10%">{item[field]}</th>
								{/each}
								{#if $data.role != 'USER'}
									<div class="flex">
										<button
											class="btn btn-info btn-lg mx-6"
											data-target="#myModal"
											data-toggle="modal"
											id="modalButtonOpen"
											style="btn"
											type="button"
											on:click={() => {
												editedContact = JSON.parse(JSON.stringify(item));
											}}
										>
											Edit
										</button>
										<button
											class="btn btn-warning btn-lg"
											id="deleteButton"
											style="btn"
											type="button"
											on:click={() => {
												if (confirm('Are you sure?')) {
													delet(item.id);
												}
											}}
										>
											Delete
										</button>
									</div>
								{/if}
							</tr>
						{/each}
					{/if}
				</tbody>
			</table>
		</div>
	</div>
	<!-- Trigger the modal with a button -->
	<!-- Modal (Pop up when detail button clicked) -->
	<div
		aria-hidden="true"
		aria-labelledby="myModalLabel"
		class="modal fade"
		id="myModal"
		role="dialog"
		tabindex="-1"
	>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button
						aria-label="Close"
						class="close"
						data-dismiss="modal"
						id="modalButtonClose"
						type="button"
					>
						Close
					</button>
					<h4 class="modal-title" id="myModalLabel">Contact Editor</h4>
					<form id="product-update-form">
						{#each inputs as item}
							<div class="col-md-4">
								<div class="form-group">
									{item}
									<input
										type="text"
										bind:value={editedContact[item]}
										name={item}
										class="form-control"
										placeholder="Enter {item}"
										id={item}
									/>
								</div>
							</div>
						{/each}
					</form>
				</div>
				<div class="modal-footer">
					<input
						class="btn btn-primary btn-md"
						on:click={submitUpdate}
						name="submit_update"
						type="button"
						value="Save Changes"
						data-dismiss="modal"
					/>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- end .container -->
