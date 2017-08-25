package ar.com.clases;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DAO.cargasvirtuales.DAOCargaVirtual;
import DAO.cliente.DAOcliente;
import DAO.cuentas.DAOCuentas;
import DAO.excel.DAOExcel;
import DAO.proveedor.DAOproveedor;
import model.CargaVirtual;
import model.Cliente;
import model.CompraCargavirtual;
import model.Cuenta;
import model.MovimientoVirtual;

public class CuentasCorrientes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DAOCuentas cuentasDAO = new DAOCuentas();
	private DAOcliente clienteDAO = new DAOcliente();
	private DAOproveedor proveedorDAO = new DAOproveedor();
	private DAOCargaVirtual cargaVirtualDAO = new DAOCargaVirtual();
	private DAOExcel excelDAO = new DAOExcel();
	
	public DAOCuentas getCuentasDAO() {
		return cuentasDAO;
	}

	public void setCuentasDAO(DAOCuentas cuentasDAO) {
		this.cuentasDAO = cuentasDAO;
	}
	
	public DAOcliente getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(DAOcliente clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public DAOproveedor getProveedorDAO() {
		return proveedorDAO;
	}

	public void setProveedorDAO(DAOproveedor proveedorDAO) {
		this.proveedorDAO = proveedorDAO;
	}

	public DAOCargaVirtual getCargaVirtualDAO() {
		return cargaVirtualDAO;
	}

	public void setCargaVirtualDAO(DAOCargaVirtual cargaVirtualDAO) {
		this.cargaVirtualDAO = cargaVirtualDAO;
	}

	public DAOExcel getExcelDAO() {
		return excelDAO;
	}

	public void setExcelDAO(DAOExcel excelDAO) {
		this.excelDAO = excelDAO;
	}

	public int insertarCCC(Cuenta cuenta){
		try{
			cuentasDAO = new DAOCuentas();
			List<Cuenta> lista = cuentasDAO.getListaClienteOrdenadaPorFecha(cuenta.getCliente());
			boolean empty = lista.isEmpty();
			if (empty) {
				Cliente cliente = clienteDAO.getClientePorId(cuenta.getCliente().getId());
				float saldoCliente = cliente.getSaldo();
				if(cuenta.getDebe() != 0){
					float debe = cuenta.getDebe();
					saldoCliente = saldoCliente + debe;
				}
				if(cuenta.getHaber() != 0){
					float haber = cuenta.getHaber();
					saldoCliente = saldoCliente - haber;
				}
				cuenta.setSaldo(saldoCliente);
				cliente.setSaldo(saldoCliente);
				int inserto = cuentasDAO.insertar(cuenta);
				int update = clienteDAO.updateCliente(cliente);
				if (inserto != 0 && update != 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				List<Cuenta> listPosterior = new ArrayList<Cuenta>();
				Cuenta cuentaAnterior = new Cuenta();
				Cliente cliente = cuenta.getCliente();
				for (Cuenta cuentasCorrientesCliente : lista) {
					String inicio = formato.format(cuentasCorrientesCliente.getFecha());
					Date fechaCuenta2 = formato.parse(inicio);
					fechaCuenta2.setHours(0);
					fechaCuenta2.setMinutes(0);
					fechaCuenta2.setSeconds(0);
					int comparaFecha = fechaCuenta2.compareTo(cuenta.getFecha());
					if(comparaFecha > 0){
						listPosterior.add(cuentasCorrientesCliente);
					}else{
						cuentaAnterior = new Cuenta();
						cuentaAnterior = cuentasCorrientesCliente;
					}
				}
				float saldo = cuentaAnterior.getSaldo();
				if(cuenta.getDebe() != 0){
					float debe = cuenta.getDebe();
					saldo = saldo + debe;
				}
				if(cuenta.getHaber() != 0){
					float haber = cuenta.getHaber();
					saldo = saldo - haber;
				}
				cuenta.setSaldo(saldo);
				int inserto = cuentasDAO.insertar(cuenta);
				if (inserto != 0) {
					boolean actualizo = true;
					for (Cuenta cuentasCorrientesCliente : listPosterior) {
						//float sldo = cuentasCorrientesCliente.getSaldo();
						if(cuentasCorrientesCliente.getDebe() != 0){	
							float debe = cuentasCorrientesCliente.getDebe();
							saldo = saldo + debe;
						}
						if(cuentasCorrientesCliente.getHaber() != 0){
							float haber = cuentasCorrientesCliente.getHaber();
							saldo = saldo - haber;
						}
						cuentasCorrientesCliente.setSaldo(saldo);
//						saldo = sldo;
						int updateCC = cuentasDAO.update(cuentasCorrientesCliente);
						if (updateCC == 0) {
							actualizo = false;
						}
					}
					cliente.setSaldo(saldo);
					int update = clienteDAO.updateCliente(cliente);
					if (actualizo && update != 0) {
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int insertarCCP(Cuenta cuenta){
		try{
			cuentasDAO = new DAOCuentas();
			List<Cuenta> lista = cuentasDAO.getListaClienteOrdenadaPorFecha(cuenta.getCliente());
			boolean empty = lista.isEmpty();
			if (empty) {
				Cliente proveedor = cuenta.getCliente();
				float saldoProveedor = proveedor.getSaldo();
				if(cuenta.getDebe() != 0){
					float debe = cuenta.getDebe();
					saldoProveedor = saldoProveedor + debe;
				}
				if(cuenta.getHaber() != 0){
					float haber = cuenta.getHaber();
					saldoProveedor = saldoProveedor - haber;
				}
				cuenta.setSaldo(saldoProveedor);
				proveedor.setSaldo(saldoProveedor);
				int inserto = cuentasDAO.insertar(cuenta);
				int update = proveedorDAO.updateProveedor(proveedor);
				if (inserto != 0 && update != 0) {
					return 1;
				} else {
					return 0;
				}
			} else {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				List<Cuenta> listPosterior = new ArrayList<Cuenta>();
				Cuenta cuentaAnterior = new Cuenta();
				Cliente proveedor = cuenta.getCliente();
				for (Cuenta cuentasCorrientesProveedore : lista) {
					String inicio = formato.format(cuentasCorrientesProveedore.getFecha());
					Date fechaCuenta2 = formato.parse(inicio);
					fechaCuenta2.setHours(0);
					fechaCuenta2.setMinutes(0);
					fechaCuenta2.setSeconds(0);
					int comparaFecha = fechaCuenta2.compareTo(cuenta.getFecha());
					if(comparaFecha > 0){
						listPosterior.add(cuentasCorrientesProveedore);
					}else{
						cuentaAnterior = new Cuenta();
						cuentaAnterior = cuentasCorrientesProveedore;
					}
				}
				float saldo = cuentaAnterior.getSaldo();
				if(cuenta.getDebe() != 0){
					float debe = cuenta.getDebe();
					saldo = saldo + debe;
				}
				if(cuenta.getHaber() != 0){
					float haber = cuenta.getHaber();
					saldo = saldo - haber;
				}
				cuenta.setSaldo(saldo);
				int inserto = cuentasDAO.insertar(cuenta);
				if (inserto != 0) {
					boolean actualizo = true;
					for (Cuenta cuentasCorrientesProveedore : listPosterior) {
//						float sldo = cuentasCorrientesProveedore.getSaldo();
						if(cuentasCorrientesProveedore.getDebe() != 0){		
							float debe = cuentasCorrientesProveedore.getDebe();
							saldo = saldo + debe;
						}
						if(cuentasCorrientesProveedore.getHaber() != 0){
							float haber = cuentasCorrientesProveedore.getHaber();
							saldo = saldo - haber;
						}
						cuentasCorrientesProveedore.setSaldo(saldo);
//						saldo = sldo;
						int updateCC = cuentasDAO.update(cuentasCorrientesProveedore);
						if (updateCC == 0) {
							actualizo = false;
						}
					}
					proveedor.setSaldo(saldo);
					int update = proveedorDAO.updateProveedor(proveedor);
					if (actualizo && update != 0) {
						return 1;
					} else {
						return 0;
					}
				} else {
					return 0;
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public int procesoActualizaSaldos() {
		List<Cliente> listaClientes = clienteDAO.getListadoClientes();
		int retorno = 1;
		System.out.println("Lista Cliente: " + listaClientes.size());
		for (Cliente cliente : listaClientes) {
			System.out.println("Cliente: " + cliente.getNombre());
			List<Cuenta> listaCuenta = cuentasDAO.getListaClienteOrdenadaPorFecha(cliente);
			float saldo = 0;
			System.out.println("Lista Cuenta: " + listaCuenta.size());
			for (Cuenta cuenta : listaCuenta) {
				if(cuenta.getDebe() != 0){					
					saldo = saldo + cuenta.getDebe();
				}
				if(cuenta.getHaber() != 0){
					saldo = saldo - cuenta.getHaber();
				}
				cuenta.setSaldo(saldo);
				int updt = cuentasDAO.update(cuenta);
				if (updt == 0) {
					retorno = 0;
				}
			}
			System.out.println("Saldo: " + saldo);
			cliente.setSaldo(saldo);
			int update = clienteDAO.updateCliente(cliente);
			if (update == 0) {
				retorno = 0;
			}
		}
		List<Cliente> listaProveedores = proveedorDAO.getListadoProveedores();
		for (Cliente proveedor : listaProveedores) {
			List<Cuenta> listaCuenta = cuentasDAO.getListaClienteOrdenadaPorFecha(proveedor);
			float saldo = 0;
			for (Cuenta cuenta : listaCuenta) {
				if(cuenta.getDebe() != 0){					
					saldo = saldo + cuenta.getDebe();
				}
				if(cuenta.getHaber() != 0){
					saldo = saldo - cuenta.getHaber();
				}
				cuenta.setSaldo(saldo);
				int updt = cuentasDAO.update(cuenta);
				if (updt == 0) {
					retorno = 0;
				}
			}
			proveedor.setSaldo(saldo);
			int update = proveedorDAO.updateProveedor(proveedor);
			if (update == 0) {
				retorno = 0;
			}
		}
		return retorno;
	}
	
	public int procesoActualizaSaldosVirtuales() {
		int retorno = 1;
		Cliente cargaVirtual = clienteDAO.getClientePorId(2);
		System.out.println("Cliente: " + cargaVirtual.getNombre());
		List<Cuenta> listaCuenta = cuentasDAO.getListaClienteOrdenadaPorFecha(cargaVirtual);
		float saldo = 0;
		System.out.println("Lista Cuenta: " + listaCuenta.size());
		for (Cuenta cuenta : listaCuenta) {
			System.out.println("Saldo: " + saldo);
			if(cuenta.getDebe() != 0){	
				float debe = cuenta.getDebe();
				System.out.println("Debe: " + debe);
				saldo = saldo + debe;
			}
			if(cuenta.getHaber() != 0){
				float haber = cuenta.getHaber();
				System.out.println("Haber: " + haber);
				saldo = saldo - haber;
			}
			System.out.println("Nuevo Saldo: " + saldo);
			cuenta.setSaldo(saldo);
			int updt = cuentasDAO.update(cuenta);
			if (updt == 0) {
				retorno = 0;
			}
		}
		System.out.println("Saldo Definitivo: " + saldo);
		cargaVirtual.setSaldo(saldo);
		int update = clienteDAO.updateCliente(cargaVirtual);
		if (update == 0) {
			retorno = 0;
		}
		return retorno;
	}
	
	public int procesoActualizaBolsaVirtual() {
		int retorno = 1;
		List<CargaVirtual> listaCargaVirtuals = cargaVirtualDAO.getListadoCargas();
		for (CargaVirtual cargaVirtual : listaCargaVirtuals) {
			Cliente virtual = clienteDAO.getClientePorId(cargaVirtual.getCliente().getId());
			List<MovimientoVirtual> listaMovimientoVirtuals = excelDAO.getLista(cargaVirtual);
			List<Cuenta> listaCuentas = cuentasDAO.getListaClienteOrdenadaPorFecha(virtual);
			List<CompraCargavirtual> listaCompraCargavirtuals = cargaVirtualDAO.getListadoCompraCargas(cargaVirtual);
			float saldoContra = 0;
			float saldoFavor = 0;
			for (MovimientoVirtual movimientoVirtual : listaMovimientoVirtuals) {
				float monto = movimientoVirtual.getMonto();
				saldoContra = saldoContra + monto;
			}
			for (Cuenta cuenta : listaCuentas) {
				if (cuenta.getDetalle().contains("Acreditacion - ")) {
					saldoFavor = saldoFavor + cuenta.getMonto();
				}
				if (cuenta.getDetalle().contains("Debito - ")) {
					saldoContra = saldoContra + cuenta.getMonto();
				}
			}
			for (CompraCargavirtual compraCargavirtual : listaCompraCargavirtuals) {
				float monto = compraCargavirtual.getCantidadMonto();
				saldoFavor = saldoFavor + monto;
			}
			float saldoFinal = saldoFavor - saldoContra;
			cargaVirtual.setCantidadMonto(saldoFinal);
			int update = cargaVirtualDAO.updateCargaVirtual(cargaVirtual);
			if (update == 0) {
				retorno = 0;
			}
		}
		return retorno;
	}

}
