import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import { AppDispatch } from '../../app/store'
import axios from 'axios'
import {
  formatYearDate,
  previousMonth as previous,
  nextMonth as next,
} from '../../common/utils/yearDate'

interface Payment {
  id: number
  description: String
  amount: number
  paidAt: String
  createdAt: String
  updatedAt: String
}

export interface PaymentsState {
  payments: Payment[]
  loading: 'idle' | 'pending' | 'succeeded' | 'failed'
  creating: 'idle' | 'pending' | 'succeeded' | 'failed'
  yearDate: String
}

const initialState = {
  payments: [],
  loading: 'idle',
  creating: 'idle',
  yearDate: formatYearDate(new Date()),
} as PaymentsState

const paymentsSlice = createSlice({
  name: 'payments',
  initialState,
  reducers: {
    resetCreating(state) {
      state.creating = 'idle'
    },
    previousMonth(state) {
      state.yearDate = previous(state.yearDate)
    },
    nextMonth(state) {
      state.yearDate = next(state.yearDate)
    },
  },
  extraReducers: (builder) => {
    builder.addCase(fetchPayments.pending, (state, action) => {
      state.loading = 'pending'
    })
    builder.addCase(fetchPayments.fulfilled, (state, action) => {
      state.payments = action.payload
      state.loading = 'succeeded'
    })
    builder.addCase(fetchPayments.rejected, (state, action) => {
      state.loading = 'failed'
    })
    builder.addCase(createPayment.fulfilled, (state, action) => {
      state.creating = 'succeeded'
    })
    builder.addCase(createPayment.rejected, (state, action) => {
      state.creating = 'failed'
    })
    builder.addCase(deletePayment.fulfilled, (state, action) => {
      state.creating = 'failed'
      state.payments = state.payments.filter(
        (payment) => payment.id !== action.payload
      )
    })
  },
})

export default paymentsSlice.reducer
export const { resetCreating, previousMonth, nextMonth } = paymentsSlice.actions

// Thunk

export const fetchPayments = createAsyncThunk(
  'payments/getPayments',
  async (yearDate: String) => {
    const result = await axios.get(
      `http://${process.env.NEXT_PUBLIC_SERVER_DOMAIN}/payments?yearDate=${yearDate}`
    )
    return result.data
  }
)

interface newPaymentForm {
  description: string
  amount: string
  paidAt: string
}

export const createPayment = createAsyncThunk(
  'payments/createPayment',
  async (form: newPaymentForm) => {
    const date = new Date(form.paidAt)
    const paidAt = date.toISOString().split('.')[0] + '+09:00'
    const body = {
      description: form.description,
      amount: parseInt(form.amount),
      paidAt,
    }
    const header = {
      'Content-Type': 'application/json',
    }
    await axios.post(
      `http://${process.env.NEXT_PUBLIC_SERVER_DOMAIN}/payments`,
      body,
      {
        headers: header,
      }
    )
  }
)

export const deletePayment = createAsyncThunk(
  'payments/deletePayment',
  async (id: number) => {
    const header = {
      'Content-Type': 'application/json',
    }
    await axios.delete(
      `http://${process.env.NEXT_PUBLIC_SERVER_DOMAIN}/payments/${id}`,
      {
        headers: header,
      }
    )
    return id
  }
)
