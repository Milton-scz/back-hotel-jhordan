package com.jhordan.Entity.PageEntity;

public class PageInfo {
    private int totalPaginas;
    private long totalElementos;
    private int paginaActual;
    private int pageSize;

    public PageInfo(int totalPaginas, long totalElementos, int paginaActual, int pageSize) {
        this.totalPaginas = totalPaginas;
        this.totalElementos = totalElementos;
        this.paginaActual = paginaActual;
        this.pageSize = pageSize;
    }

	public int getTotalPaginas() {
		return totalPaginas;
	}

	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}

	public long getTotalElementos() {
		return totalElementos;
	}

	public void setTotalElementos(long totalElementos) {
		this.totalElementos = totalElementos;
	}

	public int getPaginaActual() {
		return paginaActual;
	}

	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
