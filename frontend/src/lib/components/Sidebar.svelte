<!-- Sidebar.svelte -->
<script lang="ts">
	import brasao from "$lib/images/brasao.png";
	import UserComponent from "./UserComponent.svelte";

	const menuOptions = [
	{
		id: "PAINEL",
		label: "Painel de Controle",
		children: [
			{ label: "Indicadores", href: "/dados/indicadores"},
			{ label: "Empresa", href: "/consultar/empresa"},
			{ label: "Feirantes", href: "/consultar/feirantes"}
		]
	}, 
	{
		id: "CADASTRAR",
		label: "Cadastrar",
		children: [
			{label: "Empresa", href: "/cadastrar/empresa"},
			{label: "Feirante", href: "/cadastrar/feirante"},
			{label: "Cidade", href: "/cadastrar/cidade"}
		]
	},
	{
		id: "CONSULTAR",
		label: "Consultar",
		children: [
			{label: "Empresa", href:"/consultar/empresa"},
			{label: "Feirante", href:"/consultar/feirante"}
		]
	},
	{
		id: "RELATORIO",
		label: "Relatório",
		children: []
	}
]

	let toggle = $state<string | null> (null)
	function toggleFunction(id: string){
		if(toggle === id){
			toggle =  null
		}
		else{
			toggle = id
		}
	}

</script>

<nav class="flex flex-col items-center gap-4 p-5">
	<a href="/home"><img src={brasao} alt="Brasão" class="w-24 mb-3 md:mb-10" /></a>
	<div class="mb-3 md:hidden ">
			<UserComponent />
	</div>

	<div class="">
		<div class="flex flex-col">
			{#each menuOptions as menu}
				<div class="flex ">
					<button type="button" class="text-white hover:bg-[#88A2C5] flex rounded-lg text-lg text-center items-center p-1 border-2 border-transparent  w-60 md:w-65 " class:border-white ={toggle === menu.id} class:bg-[#88A2C5] = {toggle === menu.id} onclick={()=> toggleFunction(menu.id)}>
						<span class="w-45 ml-5 flex justify-start p-1 font-semibold">{menu.label}</span>
						<div class="flex justify-end mr-6" hidden={menu.children.length === 0}>
							<svg class="w-6 h-6 text-white dark:text-white transform transition-transform duration-300 {toggle === menu.id ? 'rotate-180': ""}"   aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" viewBox="0 0 24 24">
							<path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 9-7 7-7-7"/>
							</svg>
						</div>
					</button>
				</div>

				{#if toggle === menu.id}
					{#each menu.children as submenu }
						<a href={submenu.href} class="text-sm ml-10 mt-1 font-light">{submenu.label}</a>
						<hr class="text-white my-1 w-45 ml-10">
					{/each}
				{/if}
			{/each}
		</div>
	</div>

			

			
		
</nav>
